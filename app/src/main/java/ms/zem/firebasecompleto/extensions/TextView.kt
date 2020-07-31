package ms.zem.firebasecompleto.extensions

import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun TextView.dataValida(formato: String = "dd/MM/yyyy"): Boolean{
    return try {
        SimpleDateFormat(formato, Locale.getDefault()).
            parse(this.text.toString())
        true
    } catch (e: Exception){
        false
    }
}

fun TextView.senhaValida(): Boolean{
    return try {
        this.text?.length == 6
    } catch (e: Exception){
        false
    }
}

fun TextView.toDate(formato: String = "dd/MM/yyyy"): Date{
    return try {
        val data = SimpleDateFormat(formato, Locale.getDefault())
            .parse(this.text.toString())
        data ?: Date()
    } catch (e: Exception){
        Date()
    }
}

fun TextView.snapshot(snapshot: DataSnapshot){
    if (snapshot.exists()){
        if (snapshot.value is String){
            this.text = snapshot.value as String
        } else {
            this.text = snapshot.value.toString()
        }
    } else {
        this.text = null
    }
}