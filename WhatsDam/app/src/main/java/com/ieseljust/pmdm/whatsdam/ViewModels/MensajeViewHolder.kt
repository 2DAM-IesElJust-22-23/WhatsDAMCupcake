package com.ieseljust.pmdm.whatsdam.ViewModels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ieseljust.pmdm.whatsdam.R
import com.ieseljust.pmdm.whatsdam.model.Mensaje
import com.ieseljust.pmdm.whatsdam.model.mensajesEnviados
import com.ieseljust.pmdm.whatsdam.repository.MissatgesRepository

/**
 * Clase `MyViewHolder` que representa un ViewHolder para un mensaje en el RecyclerView.
 *
 * @constructor Crea una instancia de MyViewHolder.
 * @param itemView La vista que se va a enlazar con el ViewHolder.
 */
class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // Propiedades de clase utilizadas para hacer referencia a las vistas de texto en un ViewHolder.
    // Estas propiedades se inicializan en el constructor de la clase MyViewHolder.

    private val mensaje_texto = itemView.findViewById(R.id.msg_text) as TextView
    private val hora = itemView.findViewById(R.id.msg_me_timestamp) as TextView

    /**
     * Enlaza los datos de un mensaje con la vista del ViewHolder.
     *
     * @param mensaje El mensaje que se va a mostrar en la vista.
     */
    fun bind(mensaje: Mensaje, eventListener: Any?) {
        mensaje_texto.text = mensaje.mensaje
        hora.text = mensaje.hora
    }
}

/**
 * Clase `MyAdapter` que actúa como un adaptador para el RecyclerView que muestra mensajes.
 *
 * @constructor Crea una instancia de MyAdapter.
 * @param mensajes La lista de mensajes que se mostrarán en el RecyclerView.
 */
class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    /**
     * Crea y devuelve una nueva instancia de MyViewHolder.
     *
     * @param parent El ViewGroup al que se va a asociar la vista.
     * @param viewType El tipo de vista (no se utiliza en este caso).
     * @return Un nuevo ViewHolder que contiene la vista del mensaje.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.my_msg_viewholder, parent, false)
        return MyViewHolder(itemView)
    }

    /**
     * Enlaza los datos de un mensaje con un ViewHolder específico.
     *
     * @param holder El ViewHolder en el que se mostrará el mensaje.
     * @param position La posición del mensaje en la lista.
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        // Obtiene una instancia única del repositorio
        val repository = MissatgesRepository.getInstance()

        val listaMensajes = repository.getMissatges()

        val mensaje = listaMensajes[position]

        holder.bind(mensaje,null)
    }

    /**
     * Obtiene el número total de elementos en la lista de mensajes.
     *
     * @return El número de mensajes en la lista.
     */
    override fun getItemCount(): Int {
        return MissatgesRepository.getInstance().getNumMissatges()

    }
    /**
     * Notifica la eliminación de un elemento en la lista de mensajes.
     *
     * @param index El índice del elemento eliminado.
     */
    fun notifyItemRemoved(index: Boolean) {

    }

}

