package ms.zem.firebasecompleto.ui.storage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_storage_upload.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.ui.BaseActivity
import ms.zem.firebasecompleto.utils.CameraUtil
import ms.zem.firebasecompleto.utils.ImageDownloadUtil
import ms.zem.firebasecompleto.utils.StorageUtil

class StorageUploadActivity : BaseActivity() {

    val storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage_upload)

        toolbar.title = "Storage Upload"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnUpload.setOnClickListener {
            StorageUtil.uploadImage(imgUpload, supportFragmentManager)
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
                CameraUtil.getImage(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(Intent.createChooser(intent, "Escolha uma imagem"), GALERIA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALERIA && resultCode == Activity.RESULT_OK && data != null) {
            data.data?.let { uri ->
                ImageDownloadUtil.downloadGlide(uri, imgUpload)
            }

        } else if (requestCode == CAMERA && resultCode == Activity.RESULT_OK) {
            CameraUtil.activityResult(imgUpload)
        }
    }

    companion object {
        const val TAG = "UploadActivity"
        const val PROVIDER = "ms.zem.firebasecompleto"
        const val GALERIA = 1
        const val CAMERA = 2
    }

}