package ms.zem.firebasecompleto.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.FragmentManager
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

object StorageUtil {

    private val storage = FirebaseStorage.getInstance()
    private const val IMAGEPATH = "imagem"

    fun downloadImage(
        name: String,
        imageView: ImageView,
        progressBar: ProgressBar?
    ) {
        val reference = storage.reference.child(IMAGEPATH).child(name)
        reference.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val url = task.result.toString()
                ImageDownloadUtil.downloadGlide(url, imageView, progressBar)
            }
        }
    }

    fun deleteImage(
        name: String,
        imageView: ImageView? = null,
        progressBar: ProgressBar? = null,
        listener: ((sucesso: Boolean) -> Unit)? = null
    ) {
        progressBar?.let { progressBar.visibility = View.VISIBLE }
        val reference = storage.reference.child(IMAGEPATH).child(name)
        reference.delete().addOnCompleteListener {task ->
            if (task.isSuccessful){
                imageView?.setImageDrawable(null)
            }
            progressBar?.visibility = View.GONE
            listener?.invoke(task.isSuccessful)
        }
    }

    fun uploadImage(imageView: ImageView, supportFragmentManager: FragmentManager) {
        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "")
        val reference = storage.reference.child("upload").child("imagens")
        val nome = reference.child("IMG_"+ nomeDeArquivo()+".jpg")
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val uploadTask = nome.putBytes(bytes.toByteArray())
            .addOnCompleteListener {task ->
                dialogProgress.dismiss()
                if (task.isSuccessful){
                    AlertDialogUtil.init(imageView.context).sucesso("imagem enviada")
                } else {
                    AlertDialogUtil.init(imageView.context).erro("erro ao enviar imagem")
                }
            }
    }


}