package ms.zem.firebasecompleto.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.ui.cadastro.CadastroActivity
import ms.zem.firebasecompleto.ui.login.LoginFirebaseActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Thread(Runnable {
            Thread.sleep(1000)
            startActivity(Intent(this, CadastroActivity::class.java))
            finish()
        }).start()
    }
}