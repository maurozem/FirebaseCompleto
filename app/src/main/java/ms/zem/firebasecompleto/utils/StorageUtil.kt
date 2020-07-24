package ms.zem.firebasecompleto.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.google.firebase.storage.FirebaseStorage

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



}