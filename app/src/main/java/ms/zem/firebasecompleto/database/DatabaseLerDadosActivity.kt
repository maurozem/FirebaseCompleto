package ms.zem.firebasecompleto.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_database_ler_dados.*
import ms.zem.firebasecompleto.R
import ms.zem.firebasecompleto.utils.DatabaseUtil

class DatabaseLerDadosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_ler_dados)

        val db = DatabaseUtil()
        db.child(arrayOf("BD", "Gerente"))

        db.ler("0"){
            db.ler(tvNome,"nome")
            db.ler(tvIdade, "idade")
            db.ler(tvFumante,"fumante")
        }

        db.ler("1"){ snapshot ->
            exibirGerente(snapshot.getValue<Gerente>())
        }
    }

    private fun exibirGerente(gerente: Gerente?){
        tvNome2.text = gerente?.nome
        tvIdade2.text = gerente?.idade.toString()
        tvFumante2.text = gerente?.fumante.toString()
    }

}