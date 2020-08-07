package ms.zem.firebasecompleto.ui.empresa

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_lista_empresa.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import ms.zem.firebasecompleto.ui.DatabaseActivity
import ms.zem.firebasecompleto.R

class ListaEmpresaActivity : DatabaseActivity() {

    private val empresas: ArrayList<Empresa> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_empresa)

        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.lista_de_empresas)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        readFirebase()
        iniciarRecyclerView()
    }

    private fun readFirebase(){
        db.child(arrayOf("BD", "Empresas"))
        db.setChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val id = snapshot.key
                val empresa = snapshot.getValue(Empresa::class.java)

                empresa?.let {
                    id?.let { empresa.id = id }
                    empresas.add(it)
                }
                rvListaEmpresa.adapter?.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun iniciarRecyclerView() {
        rvListaEmpresa.layoutManager = LinearLayoutManager(this)
        rvListaEmpresa.adapter = ListaEmpresaAdapter(empresas) {empresa ->
            empresa.nome?.let { mensagem(this, it) }
        }
    }
}