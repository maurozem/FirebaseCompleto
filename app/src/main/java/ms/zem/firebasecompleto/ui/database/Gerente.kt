package ms.zem.firebasecompleto.ui.database

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Gerente(
    var nome: String? = "",
    var idade: Int? = 0,
    var fumante: Boolean? = false
)
