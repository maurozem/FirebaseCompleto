package ms.zem.firebasecompleto.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.core.content.FileProvider
import ms.zem.firebasecompleto.ui.storage.StorageUploadActivity
import java.io.File
import java.io.IOException

object CameraUtil {

    private lateinit var currentPhotoPath: String

    fun activityResult(imageView: ImageView){
        currentPhotoPath.let {
            File(currentPhotoPath).also { file ->
                Uri.fromFile(file).also { uri ->
                    ImageDownloadUtil.downloadGlide(uri, imageView)
                }
            }
        }
    }

    fun getImage(activity: Activity) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity.packageManager)?.also {
                try {
                    FileProvider.getUriForFile(activity, StorageUploadActivity.PROVIDER,
                            createImageFile(activity)).also { uri ->
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                        activity.startActivityForResult(takePictureIntent, StorageUploadActivity.CAMERA)
                    }
                } catch (ex: IOException) {
                    Log.e(StorageUploadActivity.TAG, "getImageFromCamera: ${ex.stackTrace}")
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(activity: Activity): File {
        val nome: String = nomeDeArquivo()
        val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG${nome}", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }
}