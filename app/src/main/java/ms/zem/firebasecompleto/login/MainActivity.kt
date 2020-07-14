package ms.zem.firebasecompleto.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import ms.zem.firebasecompleto.BaseActivity
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.cadastro.CadastroActivity
import ms.zem.firebasecompleto.extensions.enableDisable

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin.enableDisable(user == null)
        btnCadastro.enableDisable(user == null)
        btnLogoff.enableDisable(user != null)

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        btnCadastro.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
        btnLogoff.setOnClickListener {
            showDialog(this, R.string.confirma_logoff) {
                mensagem(this, "testando 1 2 3")
            }
        }

    }
}