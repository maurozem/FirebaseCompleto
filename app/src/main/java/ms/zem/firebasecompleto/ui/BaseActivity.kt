package ms.zem.firebasecompleto.ui

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

open class BaseActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    protected fun mensagem(context: Context, msg: Int) {
        mensagem(context, getString(msg))
    }

    protected fun mensagem(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun snack(view: View, string: String) {
        Snackbar.make(view, string, Snackbar.LENGTH_SHORT).show()
    }

}