package ms.zem.firebasecompleto.extensions

fun Any?.justNumbers(): String {
    return if (this is String) {
        this.replace("[^0-9]".toRegex(), "")
    } else {
        this.toString().replace("[^0-9]".toRegex(), "")
    }
}


