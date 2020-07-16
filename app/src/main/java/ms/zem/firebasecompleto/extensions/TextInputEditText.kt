package ms.zem.firebasecompleto.extensions

import com.google.android.material.textfield.TextInputEditText
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun TextInputEditText.dataValida(formato: String = "dd/MM/yyyy"): Boolean{
    return try {
        SimpleDateFormat(formato, Locale.getDefault()).
            parse(this.text.toString())
        true
    } catch (e: Exception){
        false
    }
}

fun TextInputEditText.senhaValida(): Boolean{
    return try {
        this.text?.length == 6
    } catch (e: Exception){
        false
    }
}