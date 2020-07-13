package ms.zem.firebasecompleto.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.cadastro.CadastroActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        btnCadastro.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
            finish()
        }
    }
}