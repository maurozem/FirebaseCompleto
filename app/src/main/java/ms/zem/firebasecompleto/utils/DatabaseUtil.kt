package ms.zem.firebasecompleto.utils

import android.util.Log
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ms.zem.firebasecompleto.extensions.snapshot

class DatabaseUtil {

    private val database = FirebaseDatabase.getInstance()
    private var reference = database.reference
    private lateinit var snapshot: DataSnapshot

    fun child(childList: Array<String>){
        for(child in childList){
            reference = reference.child(child)
        }
    }

    fun ler(child: String, sucesso: (snapshot: DataSnapshot) -> Unit){
        reference
            .child(child)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    this@DatabaseUtil.snapshot = snapshot
                    sucesso.invoke(snapshot)
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("DatabaseUtil", error.message)
                }
            })
    }

    fun ler(textView: TextView, string: String){
        textView.snapshot(snapshot.child(string))
    }

}
