package ms.zem.firebasecompleto.login

import android.os.Bundle
import com.blankj.utilcode.util.NetworkUtils
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import ms.zem.firebasecompleto.BaseActivity
import ms.zem.firebasecompleto.R

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        toolbar.title = getString(R.string.login)
        setSupportActionBar(toolbar)
        flipper.displayedChild = 1

        btnLogin.setOnClickListener {
            if (NetworkUtils.isConnected()) {
                if (!   (iedtEmail.text.toString().isNullOrEmpty()
                            or iedtSenha.text.toString().isNullOrEmpty())  ) {
                    verificarPermissao()
                } else {
                    mensagem(this, "email/senha inválido(s)")
                    iedtEmail.requestFocus()
                }
            } else {
                mensagem(this, "network dowm??")
                flipper.displayedChild = 2
                flipperError.text = "network down"
            }
        }
    }

    private fun verificarPermissao() {
        flipper.displayedChild = 0
        firebaseAuth.signInWithEmailAndPassword(
            iedtEmail.text.toString(),
            iedtSenha.text.toString()
        ).addOnCompleteListener { task ->
            flipper.displayedChild = 1
            if (task.isSuccessful) {
                mensagem(this, "email/senha verificados")
            } else {
                mensagem(this, "email/senha inválido(s)")
            }

        }
    }


}