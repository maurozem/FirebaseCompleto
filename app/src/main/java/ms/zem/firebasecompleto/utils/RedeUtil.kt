package ms.zem.firebasecompleto.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


object RedeUtil {

    fun statusInternet(context: Context): Boolean {
        val conexao =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val rede = conexao.getNetworkCapabilities(conexao.activeNetwork)
            if (rede != null) {
                if (rede.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    true
                } else rede.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            } else {
                false
            }
        } else {
            val info = conexao.activeNetworkInfo
            info != null && info.isConnected
        }
    }

}