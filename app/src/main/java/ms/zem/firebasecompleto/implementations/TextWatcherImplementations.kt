package ms.zem.firebasecompleto.implementations

import android.text.Editable
import android.text.TextWatcher
import ms.zem.firebasecompleto.extensions.justNumbers

class TextWatcherCelular : TextWatcher {
    var formatando = false
    override fun afterTextChanged(s: Editable?) {
        if (!formatando) {
            val numero = s.justNumbers()
            val tamanho = numero.length
            formatando = true
            val ss = StringBuffer()
            ss.append(numero)
            if (tamanho > 2) {
                ss.insert(0, "(")
                ss.insert(3, ") ")
            }
            if (tamanho in 8..10) {
                ss.insert(9, "-")
            } else if (tamanho >= 11) {
                ss.insert(10, "-")
            }
            s?.clear()
            s?.append(ss)
            formatando = false
        }
    }
    override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}

class TextWatcherData: TextWatcher {
    var formatando = false
    override fun afterTextChanged(s: Editable?) {
        if (!formatando) {
            val numero = s.justNumbers()
            val tamanho = numero.length
            formatando = true
            val ss = StringBuffer()
            ss.append(numero)
            if (tamanho > 2) {
                ss.insert(2, "/")
            }
            if (tamanho > 4) {
                ss.insert(5, "/")
            }
            s?.clear()
            s?.append(ss)
            formatando = false
        }
    }
    override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}

class TextWatcherCPF: TextWatcher {
    var formatando = false
    override fun afterTextChanged(s: Editable?) {
        if (!formatando) {
            val numero = s.justNumbers()
            val tamanho = numero.length
            formatando = true
            val ss = StringBuffer()
            ss.append(numero)
            if (tamanho > 3) {
                ss.insert(3, ".")
            }
            if (tamanho > 6) {
                ss.insert(7, ".")
            }
            if (tamanho > 9) {
                ss.insert(11, "-")
            }
            s?.clear()
            s?.append(ss)
            formatando = false
        }
    }
    override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}