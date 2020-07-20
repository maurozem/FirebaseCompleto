package ms.zem.firebasecompleto.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.extensions.enableDisable
import ms.zem.firebasecompleto.ui.cadastro.CadastroActivity
import ms.zem.firebasecompleto.ui.login.LoginActivity
import ms.zem.firebasecompleto.utils.AlertDialogUtil
import java.util.*

class MainActivity : BaseActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callBackManager: CallbackManager

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
        btnAnonimo.setOnClickListener {
            logonAnonimo()
        }

        servicosGoogle()
        servicosFacebook()
        verificarUsuarioLogadoDeslogado()
    }

    private fun logonAnonimo() {

    }

    private fun logonGoogle() {
        startActivityForResult(googleSignInClient.signInIntent, GOOGLE_SIGN_IN)
    }

    private fun logonFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this,
            Arrays.asList("public_profile", "email"));
    }

    private fun servicosGoogle(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun servicosFacebook() {
        callBackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callBackManager,
            object : FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?) {
                    result?.accessToken?.let { handleFacebookAccessToken(it) }
                }
                override fun onCancel() {
                    mensagem(this@MainActivity,"Autenticação cancelada pelo usuário")
                }
                override fun onError(error: FacebookException?) {
                    mensagem(this@MainActivity,"Erro ao tentar autenticar.\n" +
                            "${error?.message}")
                    Log.e(TAG, error?.stackTrace.toString())
                }
            })
    }

    private fun logado(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return (user != null || (accessToken != null && !accessToken.isExpired))
    }

    private fun logoff() {
        AlertDialogUtil
            .init(this)
            .confirmar(getString(R.string.confirma_logoff), {
                user?.let {
                    auth.signOut()
                }
                GoogleSignIn.getLastSignedInAccount(this)?.let {
                    googleSignInClient.signOut()
                }
                AccessToken.getCurrentAccessToken()?.let {
                    LoginManager.getInstance().logOut()
                }
                verificarUsuarioLogadoDeslogado()
                recreate()
                mensagem(this, R.string.usuario_desconectado)
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callBackManager.onActivityResult(requestCode, resultCode, data);
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
                    Log.i(TAG, "firebaseAuthWithGoogle:success")
                    AlertDialogUtil
                        .init(this)
                        .sucesso("Usuário conectado"){
                            verificarUsuarioLogadoDeslogado()
                            recreate()
                        }
                } else {
                    Log.w(TAG, "firebaseAuthWithGoogle:failure", task.exception)
                    snack(constraintMain, "Falha na autenticação")
                }
            }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "handleFacebookAccessToken:success")
                    mensagem(this, "Usuário conectado")
                    verificarUsuarioLogadoDeslogado()
                    recreate()
                } else {
                    mensagem(this, "Falha na autenticação")
                    Log.w(TAG, "handleFacebookAccessToken:failure", task.exception)
                }
            }
    }

    private fun verificarUsuarioLogadoDeslogado() {
        btnLogin.enableDisable(!logado())
        btnCadastro.enableDisable(!logado())
        btnGoogle.enableDisable(!logado())
        btnFace.enableDisable(!logado())
        btnLogoff.enableDisable(logado())
    }

    companion object {
        const val TAG = "Main"
        const val GOOGLE_SIGN_IN = 1
        const val LOGIN = 2
        const val CADASTRO = 3
    }
}