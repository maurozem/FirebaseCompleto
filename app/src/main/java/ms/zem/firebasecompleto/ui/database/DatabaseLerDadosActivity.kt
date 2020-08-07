package ms.zem.firebasecompleto.ui.database

import android.os.Bundle
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_database_ler_dados.*
import ms.zem.firebasecompleto.ui.DatabaseActivity
import ms.zem.firebasecompleto.R

class DatabaseLerDadosActivity : DatabaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_ler_dados)


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