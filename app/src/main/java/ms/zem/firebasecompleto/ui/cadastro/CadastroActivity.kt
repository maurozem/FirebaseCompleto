package ms.zem.firebasecompleto.ui.cadastro

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.RegexUtils
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.extensions.dataValida
import ms.zem.firebasecompleto.extensions.senhaValida
import ms.zem.firebasecompleto.extensions.traduzErroFirebase
import ms.zem.firebasecompleto.utils.TextWatcherCPF
import ms.zem.firebasecompleto.utils.TextWatcherCelular
import ms.zem.firebasecompleto.utils.TextWatcherData
import ms.zem.firebasecompleto.ui.BaseActivity
import ms.zem.firebasecompleto.utils.AlertDialogUtil
import ms.zem.firebasecompleto.utils.DatePickerUtil

class CadastroActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        toolbar.title = getString(R.string.cadastro)
        setSupportActionBar(toolbar)

        atualizacao()
        setCheckBoxAceite()
        setMask()
        setBotaoCadastrar()
        setBotaoAtualizar()
        btnCalendario.setOnClickListener {
            DatePickerUtil.setData(iedtDataNascimento)
        }
    }

    private fun atualizacao(){
        if (user != null){
            btnCadastrar.visibility = View.GONE
            tilEmail.visibility = View.GONE
            tilSenha.visibility = View.GONE
            cbxNoticacoes.visibility = View.GONE
            tilSenha.visibility = View.GONE

            iedtNome.setText(user.displayName)
            iedtCelular.setText(user.phoneNumber)
        } else {
            btnAtualizar.visibility = View.GONE
            tilNome.visibility = View.GONE
            tilCPF.visibility = View.GONE
            tilDataNascimento.visibility = View.GONE
            tilCelular.visibility = View.GONE
            rgSexo.visibility = View.GONE
        }
    }

    private fun setBotaoAtualizar(){
        btnAtualizar.setOnClickListener {

        }
    }

    private fun setMask() {
        iedtCelular.addTextChangedListener(TextWatcherCelular())
        iedtDataNascimento.addTextChangedListener(TextWatcherData())
        iedtCPF.addTextChangedListener(TextWatcherCPF())
    }

    private fun testarSenha(): Boolean {
        if (!iedtSenha.senhaValida()) {
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
                if (testarSenha() && testarEmail()) {
                    auth.createUserWithEmailAndPassword(
                        iedtEmail.text.toString(),
                        iedtSenha.text.toString()
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            AlertDialogUtil
                                .init(this)
                                .sucesso(getString(R.string.cadastro_efetuado)) {
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
            } else {
                AlertDialogUtil
                    .init(this)
                    .erro(getString(R.string.sem_conexao_com_internet))
            }
        }
    }

    private fun testarDataNasc(): Boolean {
        if (!iedtDataNascimento.dataValida()) {
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