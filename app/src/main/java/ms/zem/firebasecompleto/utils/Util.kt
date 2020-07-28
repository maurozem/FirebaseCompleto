package ms.zem.firebasecompleto.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun nomeDeArquivo() = SimpleDateFormat("yyyyMMdd_HHmmss_").format(Date()) +
        (Random().nextInt(899)+100)