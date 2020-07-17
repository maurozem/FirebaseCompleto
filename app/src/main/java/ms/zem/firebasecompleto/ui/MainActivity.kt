package ms.zem.firebasecompleto.ui

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.ui.cadastro.CadastroActivity
import ms.zem.firebasecompleto.extensions.enableDisable
import ms.zem.firebasecompleto.ui.login.LoginActivity
import ms.zem.firebasecompleto.utils.AlertDialogUtil

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verificarUsuarioLogadoDeslogado()

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        btnCadastro.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
        btnLogoff.setOnClickListener {
            AlertDialogUtil
                .init(this)
                .confirmar(getString(R.string.confirma_logoff), {
                    auth.signOut()
                    verificarUsuarioLogadoDeslogado()
                    recreate()
                    AlertDialogUtil
                        .init(this)
                        .sucesso(getString(R.string.usuario_desconectado))
                })
        }
    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }

    private fun verificarUsuarioLogadoDeslogado() {
        btnLogin.enableDisable(user == null)
        btnCadastro.enableDisable(user == null)
        btnLogoff.enableDisable(user != null)
    }
}