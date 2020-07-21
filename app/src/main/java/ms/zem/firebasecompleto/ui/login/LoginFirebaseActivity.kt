package ms.zem.firebasecompleto.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.ui.BaseActivity
import ms.zem.firebasecompleto.ui.MainActivity
import ms.zem.firebasecompleto.ui.menu.MenuActivity

class LoginFirebaseActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_firebase)

        if (user != null){
            mensagem(this, "usuário já logado")
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        } else {
            loginFirebase()
        }
    }

    private fun loginFirebase() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.AnonymousBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build())
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setLogo(R.drawable.firebase)
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
    }

    @SuppressLint("RestrictedApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            Log.i(TAG, response.toString())

            if (resultCode == Activity.RESULT_OK) {
                mensagem(this, "autenticação efetuada")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                mensagem(this, "falha na autenticação")
            }
        }
    }

    companion object{
        val TAG = "LoginFirebase"
        val RC_SIGN_IN = 1
    }
}