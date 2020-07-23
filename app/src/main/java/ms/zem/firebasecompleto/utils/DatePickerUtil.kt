package ms.zem.firebasecompleto.utils

import android.app.DatePickerDialog
import android.widget.TextView
import ms.zem.firebasecompleto.extensions.toDate
import java.text.SimpleDateFormat
import java.util.*

object DatePickerUtil {

    fun setData(textView: TextView){
        val cal = Calendar.getInstance()
        cal.time = textView.toDate()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, day)

            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
            textView.text = sdf.format(cal.time)
        }
        DatePickerDialog(textView.context, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
    }

}