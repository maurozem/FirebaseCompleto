package ms.zem.firebasecompleto.utils

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import ms.zem.firebasecompleto.extensions.snapshot

class DatabaseUtil {

    private val database = FirebaseDatabase.getInstance()
    private var reference = database.reference
    private lateinit var snapshot: DataSnapshot

    lateinit var sucesso: (snapshot: DataSnapshot) -> Unit

    fun setChildEventListener(listener: ChildEventListener){
        reference.addChildEventListener(listener)
    }

    private var valueEventListener = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            this@DatabaseUtil.snapshot = snapshot
            sucesso.invoke(snapshot)
        }
        override fun onCancelled(error: DatabaseError) {
            Log.e("DatabaseUtil", error.message)
        }
    }

    fun child(childList: Array<String>){
        for(child in childList){
            reference = reference.child(child)
        }
    }

    fun ler(child: String, sucesso: (snapshot: DataSnapshot) -> Unit){
        this.sucesso = sucesso
        reference
            .child(child)
            .addValueEventListener(valueEventListener)
//            .addListenerForSingleValueEvent(object : ValueEventListener {}
    }

    fun ler(textView: TextView, string: String){
        textView.snapshot(snapshot.child(string))
    }

    fun removerListener(){
        reference.removeEventListener(valueEventListener)
    }

    fun incluir(context: Context, objeto: Any, sucesso: (() -> Any)?, erro: (() -> Any)?) {
        reference.push().setValue(objeto).addOnCompleteListener { task ->
            completeListener(context, task, sucesso, erro,
                "inclusão efetuada",
                "erro ao tentar incluir")
        }
    }

    fun atualizar(context: Context, indice: String, objeto: Any,
                  sucesso: (() -> Any)?, erro: (() -> Any)?) {
        val atualizacao = HashMap<String, Any>()
        atualizacao.put(indice, objeto)
        reference.updateChildren(atualizacao).addOnCompleteListener { task ->
            completeListener(context, task, sucesso, erro,
            "atualização efetuada",
            "erro ao tentar atualizar")
        }
    }

    fun remover(context: Context, indice: String,
                sucesso: (() -> Any)?, erro: (() -> Any)?) {
        reference.child(indice).removeValue().addOnCompleteListener {task ->
            completeListener(context, task, sucesso, erro,
                "remoção efetuada",
                "erro ao tentar remover")
        }
    }

    private fun completeListener(
        context: Context,
        task: Task<Void>,
        sucesso: (() -> Any)?,
        erro: (() -> Any)?,
        mensagemSucesso: String = "transação efetuada",
        mensagemErro: String = "erro ao tentar efetuar transação"
    ) {
        if (task.isSuccessful) {
            AlertDialogUtil
                .init(context)
                .sucesso(mensagemSucesso) {
                    sucesso?.invoke()
                }
        } else {
            AlertDialogUtil
                .init(context)
                .erro(mensagemErro) {
                    erro?.invoke()
                }
        }
    }

    fun ativarModoOffline(){
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        }catch (e: Exception){
            Log.e("databaseUtil", "ativarOffline: " + e.stackTrace)
        }
    }

}


