package ms.zem.firebasecompleto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ms.zem.firebasecompleto.login.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Thread(Runnable {
            Thread.sleep(2000)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }).start()
    }
}