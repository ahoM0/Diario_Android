package es.instituto.diario

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntradaAdaptador (private val context: Context) : RecyclerView.Adapter<EntradaAdaptador.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fecha: TextView
        var texto: TextView
        var boton: Button
        init {
            fecha = view.findViewById(R.id.imagenFecha)
            texto= view.findViewById(R.id.texto)
            boton = view.findViewById(R.id.boton)
        }
    }
    var elementos= mutableListOf<Entrada>()
    private   var listener:OnClickListener?=null
    public fun setListener(listener:OnClickListener){
        this.listener=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.entrada, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.elementos.size
    }
    interface OnClickListener {
        fun onClick(position: Int, model: Entrada)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fecha.text=this.elementos.get(position).fecha
        holder.texto.text=this.elementos.get(position).texto
        holder.boton.setOnClickListener{
            if(this.listener!=null){
                this.listener!!.onClick(position, this.elementos.get(position))
            }
        }

    }
}