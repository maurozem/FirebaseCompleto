package ms.zem.firebasecompleto.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun nomeDeArquivo() = SimpleDateFormat("_yyyyMMdd_HHmmss_").format(Date()) +
            Random().ints(100, 999)