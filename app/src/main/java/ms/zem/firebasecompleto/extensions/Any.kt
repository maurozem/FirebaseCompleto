package ms.zem.firebasecompleto.extensions

import kotlin.Any


    fun Any?.justNumbers(): String{
        return if (this is String){
            this.replace("[^0-9]".toRegex(), "")
        } else {
            this.toString().replace("[^0-9]".toRegex(), "")
        }
    }

