package ms.zem.firebasecompleto

import ms.zem.firebasecompleto.ui.BaseActivity
import ms.zem.firebasecompleto.utils.DatabaseUtil

open class DatabaseActivity: BaseActivity() {

    private var modoFirebaseOffline = false
    val db = DatabaseUtil()

    override fun onDestroy() {
        db.removerListener()
        super.onDestroy()
    }

    protected fun ativarModoOffline(){
        if (!modoFirebaseOffline){
            db.ativarModoOffline()
            modoFirebaseOffline = true
        }
    }
}