package ms.zem.firebasecompleto.ui.login

import android.os.Bundle
import com.blankj.utilcode.util.NetworkUtils
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import ms.zem.firebasecompleto.ui.BaseActivity
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.extensions.traduzErroFirebase
import ms.zem.firebasecompleto.utils.AlertDialogUtil

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        toolbar.title = getString(R.string.login)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        flipper.displayedChild = 1

        btnLogin.setOnClickListener {
            logar()
        }

        btnEsqueciSenha.setOnClickListener {
            enviarEmail()
        }
    }

    private fun enviarEmail() {
        if (NetworkUtils.isConnected()) {
            if (!(iedtEmail.text.toString().isEmpty())) {
                auth.sendPasswordResetEmail(iedtEmail.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            AlertDialogUtil
                                .init(this)
                                .sucesso("Email enviado")
                        } else {
                            task.exception?.message?.traduzErroFirebase()?.let {
                                AlertDialogUtil
                                    .init(this)
                                    .erro(it)
                            }
                        }
                    }
            } else {
                AlertDialogUtil
                    .init(this)
                    .aviso("Email precisa estar preenchido corretamente")
                iedtEmail.requestFocus()
            }
        } else {
            AlertDialogUtil
                .init(this)
                .erro("Sem acesso a internet")
            flipper.displayedChild = 2
            flipperError.text = "network down"
        }
    }

    private fun logar() {
        if (NetworkUtils.isConnected()) {
            if (!(iedtEmail.text.toString().isNullOrEmpty()
                        or iedtSenha.text.toString().isNullOrEmpty())
            ) {
                verificarPermissao()
            } else {
                AlertDialogUtil
                    .init(this)
                    .erro("email/senha inválido(s)")
                iedtEmail.requestFocus()
            }
        } else {
            flipper.displayedChild = 2
            flipperError.text = "Sem acesso a internet"
        }
    }

    private fun verificarPermissao() {
        flipper.displayedChild = 0
        auth.signInWithEmailAndPassword(
            iedtEmail.text.toString(),
            iedtSenha.text.toString()
        ).addOnCompleteListener { task ->
            flipper.displayedChild = 1
            if (task.isSuccessful) {
                AlertDialogUtil
                    .init(this)
                    .sucesso("login efetuado!!"){
                        finish()
                    }
            } else {
                task.exception?.message?.let { msg ->
                    AlertDialogUtil
                        .init(this)
                        .erro(msg.traduzErroFirebase())
                }
            }
        }
    }

}