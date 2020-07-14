package ms.zem.firebasecompleto

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

open class BaseActivity : AppCompatActivity(){

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    protected fun mensagem(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun snack(view: View, string: String){
        Snackbar.make(view, string, Snackbar.LENGTH_SHORT).show()
    }

    protected fun showDialog(context: Context, mensagem: Int, positiveExec: () -> Unit?){
        val builder = AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
        builder.setMessage(mensagem)
        builder.setIcon(R.drawable.ic_question)
        builder.setTitle("Atenção")
        builder.setCancelable(false)
        builder.setPositiveButton(R.string.sim) { _, _ ->
            positiveExec.invoke()
        }
        builder.setNeutralButton(R.string.cancelar) { _, _ ->
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}