package ms.zem.firebasecompleto.ui.database

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_database_atualizar.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import ms.zem.firebasecompleto.DatabaseActivity
import ms.zem.firebasecompleto.R

class DatabaseAtualizarActivity : DatabaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_atualizar)

        toolbar.title = getString(R.string.atualizar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnIncluir.setOnClickListener {
            incluir()
        }
        btnAtualizar.setOnClickListener {
            atualizar()
        }
        btnExcluir.setOnClickListener {
            remover()
        }

        ativarModoOffline()
    }

    private fun remover() {
        db.remover(this, edtPasta.text.toString(), {
            edtPasta.text = null
            edtPasta.requestFocus() },
            { edtPasta.requestFocus() })
    }

    private fun atualizar() {
        val gerente = Gerente(
            edtNome.text.toString(),
            edtIdade.text.toString().toInt(),
            false)
        db.atualizar(this, edtPasta.text.toString(), gerente, {
            edtNome.text = null
            edtIdade.text = null
            edtPasta.requestFocus() },
            { edtPasta.requestFocus() })
    }

    private fun incluir() {
        val gerente = Gerente(
            edtNome.text.toString(),
            edtIdade.text.toString().toInt(),
            false)
        db.incluir(this, gerente, {
            edtNome.text = null
            edtIdade.text = null
            edtNome.requestFocus() },
            { edtNome.requestFocus() })
    }

}