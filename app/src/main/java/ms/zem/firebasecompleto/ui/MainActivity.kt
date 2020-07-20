package ms.zem.firebasecompleto.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.extensions.enableDisable
import ms.zem.firebasecompleto.ui.cadastro.CadastroActivity
import ms.zem.firebasecompleto.ui.login.LoginActivity
import ms.zem.firebasecompleto.utils.AlertDialogUtil

class MainActivity : BaseActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verificarUsuarioLogadoDeslogado()

        btnLogin.setOnClickListener {
                startActivityForResult(Intent(this, LoginActivity::class.java), LOGIN)
        }
        btnCadastro.setOnClickListener {
            startActivityForResult(Intent(this, CadastroActivity::class.java), CADASTRO)
        }
        btnLogoff.setOnClickListener {
            logoff()
        }
        btnGoogle.setOnClickListener {
            logonGoogle()
        }
        btnFace.setOnClickListener {
            logonFacebook()
        }
    }

    private fun logonFacebook() {

    }

    private fun logonGoogle() {
        servicosGoogle()
        startActivityForResult(googleSignInClient.signInIntent, GOOGLE_SIGN_IN)
    }

    private fun logoff() {
        AlertDialogUtil
            .init(this)
            .confirmar(getString(R.string.confirma_logoff), {
                user?.let { auth.signOut() }
                servicosGoogle()
                GoogleSignIn.getLastSignedInAccount(this)?.let { googleSignInClient.signOut() }
                verificarUsuarioLogadoDeslogado()
                recreate()
                mensagem(this, R.string.usuario_desconectado)
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN){
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val userGoogle = task.getResult(ApiException::class.java)!!
                userGoogle.idToken?.let { firebaseAuthWithGoogle(it) }
            } catch (e: Exception){
                Log.e(TAG, "GOOGLE_SIGN_IN: ${e.stackTrace}", e)
                snack(constraintMain, e.message.toString())
            }
        } else {
            verificarUsuarioLogadoDeslogado()
            recreate()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "signInWithCredential:success")
                    AlertDialogUtil
                        .init(this)
                        .sucesso("Usuário conectado"){
                            verificarUsuarioLogadoDeslogado()
                            recreate()
                        }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    snack(constraintMain, "Falha na autenticação")
                }
            }
    }

    override fun onResume() {
        super.onResume()
        verificarUsuarioLogadoDeslogado()
    }

    private fun verificarUsuarioLogadoDeslogado() {
        btnLogin.enableDisable(user == null)
        btnCadastro.enableDisable(user == null)
        btnGoogle.enableDisable(user == null)
        btnLogoff.enableDisable(user != null)
    }

    private fun servicosGoogle(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    companion object {
        const val TAG = "Main"
        const val GOOGLE_SIGN_IN = 1
        const val LOGIN = 2
        const val CADASTRO = 3
    }
}