package ms.zem.firebasecompleto.ui.storage

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import ms.zem.firebasecompleto.utils.ImageDownloadUtil
import ms.zem.firebasecompleto.utils.nomeDeArquivo
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


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
            upload01()
        }
    }

    private fun upload01(){
        val reference = storage.reference.child("upload").child("imagens")

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

    private fun getImageFromCameraBITMAP() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, CAMERA)
            }
        }
    }

    private fun getImageFromCamera(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Log.e(TAG, ex.stackTrace.toString())
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "ms.zem.firebasecompleto",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, CAMERA)
                } ?: snack(layoutUpload, "erro ao criar foto")
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
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

        if (requestCode == GALERIA && resultCode == Activity.RESULT_OK && data != null){
            data.data?.let { ImageDownloadUtil.downloadGlide(it, imgUpload) }

        } else if (requestCode == CAMERA && resultCode == Activity.RESULT_OK){
            val auxFile = File(currentPhotoPath)
            val bitmap :Bitmap = BitmapFactory.decodeFile(currentPhotoPath)
            imgUpload.setImageBitmap(bitmap)

            // BITMAP
//            imgUpload.setImageBitmap(data.extras?.get("data") as Bitmap?)
        }
    }

    companion object{
        const val TAG = "UploadActivity"
        const val GALERIA = 1
        const val CAMERA = 2
    }

}