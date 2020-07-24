package ms.zem.firebasecompleto.ui.storage

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_storage_download.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.extensions.compartilhar
import ms.zem.firebasecompleto.ui.BaseActivity
import ms.zem.firebasecompleto.utils.AlertDialogUtil
import ms.zem.firebasecompleto.utils.StorageUtil


class StorageDownloadActivity : BaseActivity() {

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
            StorageUtil.deleteImage("pilares.png", imgDownload, pgbDownload) { sucesso ->
                if (sucesso) {
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
                pdf()
                return true
            }
            R.id.compartilhar -> {
                compartilhar()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun pdf() {
        mensagem(this, "não implementado")
    }

    private fun compartilhar() {
        imgDownload.compartilhar()
    }

}