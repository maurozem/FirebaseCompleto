package ms.zem.firebasecompleto.ui.storage

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_storage_download.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.extensions.compartilhar
import ms.zem.firebasecompleto.extensions.compartilhar2020
import ms.zem.firebasecompleto.ui.BaseActivity
import ms.zem.firebasecompleto.utils.AlertDialogUtil
import ms.zem.firebasecompleto.utils.StorageUtil
import java.io.ByteArrayOutputStream

class StorageDownloadActivity : BaseActivity() {

    private val urlOld =
        "https://firebasestorage.googleapis.com/v0/b/pessoal-estudo.appspot.com/o/imagem%2Fpilares.png?alt=media&token=b7678856-e984-4732-ac4c-761eff7e8318"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage_download)

        toolbar.title = "Storage Download"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnDownload.setOnClickListener {
            StorageUtil.downloadImage("pilares.png", imgDownload, pgbDownload)
        }
        btnRemover.setOnClickListener {
            StorageUtil.deleteImage("pilares.png", imgDownload, pgbDownload){ sucesso ->
                if (sucesso){
                    AlertDialogUtil.init(this)
                        .sucesso("imagem removida")
                } else {
                    AlertDialogUtil.init(this)
                        .erro("erro ao remover imagem")
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_storage_download, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.gerarPDF -> {
                gerarPDF()
                return true
            }
            R.id.compartilhar -> {
                compartilhar()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun compartilhar() {
        imgDownload.compartilhar()
    }

    private fun gerarPDF() {
        mensagem(this, "gerar pdf")
    }
}