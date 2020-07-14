package ms.zem.firebasecompleto.extensions

import android.widget.Button

fun Button.enableDisable(boolean: Boolean){
    if(boolean){
        this.alpha = 1f
        this.isEnabled = true
    } else {
        this.alpha = 0.3f
        this.isEnabled = false
    }
}