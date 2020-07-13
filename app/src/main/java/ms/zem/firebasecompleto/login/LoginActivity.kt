package ms.zem.firebasecompleto.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.toolbar.*
import ms.zem.firebasecompleto.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        toolbar.title = getString(R.string.login)
        setSupportActionBar(toolbar)
    }
}