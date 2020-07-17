package ms.zem.firebasecompleto.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import ms.zem.firebasecompleto.R

object AlertDialogUtil{

    private lateinit var builder: AlertDialog.Builder

    fun init(context: Context): AlertDialogUtil{
        builder = AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
        builder.setCancelable(false)
        return this
    }

    fun erro(mensagem: String, okExec: (() -> Unit)? = null) {
        builder.setMessage(mensagem)
        setTituloIcone(R.string.erro, R.drawable.ic_error)
        sendOK(okExec)
    }

    fun sucesso(mensagem: String, okExec: (() -> Unit)? = null) {
        builder.setMessage(mensagem)
        setTituloIcone(R.string.sucesso, R.drawable.ic_check)
        sendOK(okExec)
    }

    fun aviso(mensagem: String, okExec: (() -> Unit)? = null) {
        builder.setMessage(mensagem)
        setTituloIcone(R.string.aviso, R.drawable.ic_warning)
        sendOK(okExec)
    }

    fun confirmar(mensagem: String, positiveExec: () -> Unit, negativeExec: (() -> Unit)? = null) {
        builder.setMessage(mensagem)
        setTituloIcone(R.string.confirmacao, R.drawable.ic_question)
        builder.setPositiveButton(R.string.sim) { _, _ ->
            positiveExec.invoke()
        }
        negativeExec?.let {
            builder.setPositiveButton(R.string.nao) { _, _ ->
                negativeExec.invoke()
            }
        } ?: builder.setNegativeButton(R.string.cancelar) { _, _ -> }
        show()
    }

    private fun setTituloIcone(titulo: Int, icone: Int) {
        builder.setTitle(titulo)
        builder.setIcon(icone)
    }

    private fun sendOK(okExec: (() -> Unit)? = null) {
        builder.setNeutralButton(R.string.ok) { _, _ ->
            okExec?.let {
                it.invoke()
            }
        }
        show()
    }

    private fun show(){
        val dialog = builder.create()
        dialog.show()
    }
}