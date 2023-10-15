package com.ieseljust.pmdm.whatsdam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mensaje_texto = itemView.findViewById(R.id.msg_text) as TextView
    private val hora = itemView.findViewById(R.id.msg_me_timestamp) as TextView

    // Enlazar los datos de Mensajes con la vista
    fun bind(mensaje: Activity_messages_window.Mensaje) {
        mensaje_texto.text = mensaje.mensaje
        hora.text = mensaje.hora
    }
}

class MyAdapter(private val mensajes: List<Activity_messages_window.Mensaje>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.my_msg_viewholder, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mensaje = mensajes[position]
        holder.bind(mensaje)
    }

    override fun getItemCount(): Int {
        return mensajes.size
    }
}

