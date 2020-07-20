package ms.zem.firebasecompleto.extensions

import android.view.View

fun View.enableDisable(boolean: Boolean){
    if(boolean){
        this.alpha = 1f
        this.isEnabled = true
    } else {
        this.alpha = 0.3f
        this.isEnabled = false
    }
}