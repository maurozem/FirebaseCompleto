package ms.zem.firebasecompleto.ui.empresa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ms.zem.firebasecompleto.R

class ListaEmpresaAdapter(val empresas: List<Empresa>,
                          val clickEmpresa: ((empresa: Empresa) -> Unit)?):
        RecyclerView.Adapter<ListaEmpresaAdapter.ListaEmpresaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaEmpresaViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lista_empresa, parent, false)
        val holder = ListaEmpresaViewHolder(view)
        view.tag = holder
        return holder
    }

    override fun getItemCount(): Int {
        return empresas.size
    }

    override fun onBindViewHolder(holder: ListaEmpresaViewHolder, position: Int) {
        val empresa = empresas.get(position)
        holder.tvEmpresaItemNome.text = empresa.nome
        holder.cvEmpresaItem.setOnClickListener {
            clickEmpresa?.invoke(empresa)
        }
    }

    class ListaEmpresaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvEmpresaItemNome = itemView.findViewById<TextView>(R.id.tvEmpresaItemNome)
        val cvEmpresaItem = itemView.findViewById<CardView>(R.id.cvEmpresaItem)

    }

}