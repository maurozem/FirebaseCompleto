package ms.zem.firebasecompleto.utils

import android.app.Activity
import android.content.pm.PackageManager
import java.util.*

object Permissao {

    fun permissao( activity: Activity, requestCode: Int, permissoes: Array<String>): Boolean {
        val list = ArrayList<String>()
        for (permissao in permissoes) {
            if (activity.checkSelfPermission(permissao) != PackageManager.PERMISSION_GRANTED) {
                list.add(permissao)
            }
        }
        return if (list.isEmpty()) {
            true
        } else {
            val newPermissions: Array<String?> = arrayOfNulls(list.size)
            list.toArray<String>(newPermissions)
            activity.requestPermissions(newPermissions, requestCode)
            false
        }
    }

}