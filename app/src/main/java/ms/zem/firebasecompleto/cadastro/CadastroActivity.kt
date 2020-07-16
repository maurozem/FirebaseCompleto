package ms.zem.firebasecompleto.cadastro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.Utils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import ms.zem.firebasecompleto.BaseActivity
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.extensions.dataValida
import ms.zem.firebasecompleto.extensions.senhaValida
import ms.zem.firebasecompleto.extensions.trataErroFirebase
import ms.zem.firebasecompleto.implementations.TextWatcherCPF
import ms.zem.firebasecompleto.implementations.TextWatcherCelular
import ms.zem.firebasecompleto.implementations.TextWatcherData

class CadastroActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        toolbar.title = getString(R.string.cadastro)
        setSupportActionBar(toolbar)

        setCheckBoxAceite()
        setMask()
        setBotaoCadastrar()
    }

    private fun setMask() {
        iedtCelular.addTextChangedListener(TextWatcherCelular())
        iedtDataNascimento.addTextChangedListener(TextWatcherData())
        iedtCPF.addTextChangedListener(TextWatcherCPF())
    }

    private fun testarSenha(): Boolean {
        if (iedtSenha.senhaValida()) {
            tilSenha.requestFocus()
            tilSenha.error = getString(R.string.senha_invalida)
            tilSenha.isErrorEnabled = true
        } else {
            tilSenha.isErrorEnabled = false
        }
        return !tilSenha.isErrorEnabled
    }

    private fun setBotaoCadastrar() {
        btnCadastrar.setOnClickListener {
            if (NetworkUtils.isConnected()) {
//            if (testarDataNasc() && testarSenha() && testarEmail() && testarNome()) {
                auth.createUserWithEmailAndPassword(
                    iedtEmail.text.toString(),
                    iedtSenha.text.toString()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        snack(llCadastro, getString(R.string.cadastro_efetuado))
                    } else {
                        task.exception?.message?.let { msg ->
                            snack(
                                llCadastro,
                                msg.trataErroFirebase()
                            )
                        }

                    }
                }
//            }
            } else {
                snack(llCadastro, getString(R.string.sem_conexao_com_internet))
            }
        }
    }

    private fun testarDataNasc(): Boolean {
        if(!iedtDataNascimento.dataValida()){
            tilDataNascimento.requestFocus()
            tilDataNascimento.error = getString(R.string.data_invalida)
            tilDataNascimento.isErrorEnabled = true
        } else {
            tilDataNascimento.isErrorEnabled = false
        }
        return !tilDataNascimento.isErrorEnabled
    }

    private fun testarNome(): Boolean {
        if (iedtNome.text?.length!! < 3) {
            tilNome.requestFocus()
            tilNome.error = getString(R.string.nome_invalido)
            tilNome.isErrorEnabled = true
        } else {
            tilNome.isErrorEnabled = false
        }
        return !tilNome.isErrorEnabled
    }

    private fun testarEmail(): Boolean {
        if (iedtEmail.text?.length!! < 5 ||
            (!RegexUtils.isEmail(iedtEmail.text))
        ) {
            tilEmail.requestFocus()
            tilEmail.error = getString(R.string.email_incorreto)
            tilEmail.isErrorEnabled = true
        } else {
            tilEmail.isErrorEnabled = false
        }
        return !tilEmail.isErrorEnabled
    }

    private fun setCheckBoxAceite() {
        cbxNoticacoes.setOnClickListener {
            if (cbxNoticacoes.isChecked) {
                btnCadastrar.setBackgroundColor(this.getColor(R.color.primary_dark))
                btnCadastrar.isEnabled = true
            } else {
                btnCadastrar.setBackgroundColor(this.getColor(R.color.primary_light))
                btnCadastrar.isEnabled = false
            }
        }
    }

}