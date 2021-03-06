package ms.zem.firebasecompleto.ui.menu

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu.*
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.ui.database.DatabaseLerDadosActivity
import ms.zem.firebasecompleto.ui.BaseActivity
import ms.zem.firebasecompleto.ui.database.DatabaseAtualizarActivity
import ms.zem.firebasecompleto.ui.empresa.ListaEmpresaActivity
import ms.zem.firebasecompleto.ui.storage.StorageDownloadActivity
import ms.zem.firebasecompleto.ui.storage.StorageUploadActivity
import ms.zem.firebasecompleto.utils.Permissao

class MenuActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnDownload.setOnClickListener {
            startActivity(Intent(this, StorageDownloadActivity::class.java))
        }
        btnUpload.setOnClickListener {
            startActivity(Intent(this, StorageUploadActivity::class.java))
        }
        btnLer.setOnClickListener {
            startActivity(Intent(this, DatabaseLerDadosActivity::class.java))
        }
        btnAtualizar.setOnClickListener {
            startActivity(Intent(this, DatabaseAtualizarActivity::class.java))
        }
        btnEmpresas.setOnClickListener {
            startActivity(Intent(this, ListaEmpresaActivity::class.java))
        }
        permissoes()
    }

    fun permissoes(){
        val permissoes = listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
        Permissao.permissao(this, PERMISSOES, permissoes)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSOES) {
            for (result in grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    mensagem(this, "Aceite as permissões para o app funcionar corretamente")
                    finish()
                    break
                }
            }
        }
    }

    companion object{
        const val PERMISSOES = 1
    }
}