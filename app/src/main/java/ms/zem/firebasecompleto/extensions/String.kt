package ms.zem.firebasecompleto.extensions

fun String.traduzErroFirebase(): String{
    return when {
        this.contains("least 6 characters") -> {
            "senha deve conter 6 caracteres"
        }
        this.contains("address is badly") -> {
            "email inválido"
        }
        this.contains("address is already") -> {
            "email já cadastrado"
        }
        this.contains("interrupted connection") -> {
            "falha de conexão/conexão interrompida"
        }
        this.contains("password is invalid") -> {
            "senha inválida"
        }
        this.contains("There is no user") -> {
            "email não encontrado/não existe"
        }
        else -> {
            this
        }
    }
}