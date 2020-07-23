package ms.zem.firebasecompleto.ui.menu

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu.*
import ms.zem.firebasecompleto.R

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnDownload.setOnClickListener {

        }
        btnUpload.setOnClickListener {

        }
        btnLer.setOnClickListener {

        }
        btnAtualizar.setOnClickListener {

        }
        btnEmpresas.setOnClickListener {

        }
    }

    fun permissoes(){

        val permissoes = listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE )

    }
}