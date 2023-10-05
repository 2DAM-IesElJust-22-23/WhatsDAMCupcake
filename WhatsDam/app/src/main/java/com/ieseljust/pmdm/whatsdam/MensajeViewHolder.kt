package com.ieseljust.pmdm.whatsdam

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ieseljust.pmdm.whatsdam.Activity_messages_window

class MensajeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val mensaje_texto = itemView.findViewById(R.id.msg_text) as TextView
    val hora = itemView.findViewById(R.id.msg_me_timestamp) as TextView

    // Enlazar los datos de Mensajes con la vista
    fun bind(mensaje: Activity_messages_window.mensajes) {
        mensaje_texto.text = mensaje.mensaje
        hora.text = mensaje.hora
    }
}

