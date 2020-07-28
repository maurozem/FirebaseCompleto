package ms.zem.firebasecompleto.ui.storage

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.FileProvider
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_storage_upload.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.ui.BaseActivity
import ms.zem.firebasecompleto.utils.AlertDialogUtil
import ms.zem.firebasecompleto.utils.DialogProgress
import ms.zem.firebasecompleto.utils.ImageDownloadUtil
import ms.zem.firebasecompleto.utils.nomeDeArquivo
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class StorageUploadActivity : BaseActivity() {

    lateinit var currentPhotoPath: String
    val storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage_upload)

        toolbar.title = "Storage Upload"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnUpload.setOnClickListener {
            upload()
        }
    }

    private fun upload() {
        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "")
        val reference = storage.reference.child("upload").child("imagens")
        val nome = reference.child("IMG_"+ nomeDeArquivo()+".jpg")
        val bitmap = (imgUpload.drawable as BitmapDrawable).bitmap
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val uploadTask = nome.putBytes(bytes.toByteArray())
            .addOnCompleteListener {task ->
                dialogProgress.dismiss()
                if (task.isSuccessful){
                    AlertDialogUtil.init(this).sucesso("imagem enviada")
                } else {
                    AlertDialogUtil.init(this).erro("erro ao enviar imagem")
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_storage_upload, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_galeria -> {
                getImageFromGallery()
            }
            R.id.tirar_foto -> {
                getImageFromCamera()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getImageFromCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                try {
                    FileProvider.getUriForFile(this, PROVIDER, createImageFile()).also { uri ->
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                        startActivityForResult(takePictureIntent, CAMERA)
                    }
                } catch (ex: IOException) {
                    Log.e(TAG, "getImageFromCamera: ${ex.stackTrace}")
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val nome: String = nomeDeArquivo()
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG${nome}", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun getImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(Intent.createChooser(intent, "Escolha uma imagem"), GALERIA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALERIA && resultCode == Activity.RESULT_OK && data != null) {
            data.data?.let {uri ->
                ImageDownloadUtil.downloadGlide(uri, imgUpload)
            }

        } else if (requestCode == CAMERA && resultCode == Activity.RESULT_OK) {
            currentPhotoPath.let {
                File(currentPhotoPath).also { file ->
                    Uri.fromFile(file).also { uri ->
                        ImageDownloadUtil.downloadGlide(uri, imgUpload)
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "UploadActivity"
        const val PROVIDER = "ms.zem.firebasecompleto"
        const val GALERIA = 1
        const val CAMERA = 2
    }

}