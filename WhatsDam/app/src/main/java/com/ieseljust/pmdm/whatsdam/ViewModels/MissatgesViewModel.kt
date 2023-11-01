package com.ieseljust.pmdm.whatsdam.ViewModels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ieseljust.pmdm.whatsdam.model.Mensaje
import com.ieseljust.pmdm.whatsdam.repository.MissatgesRepository
import com.ieseljust.pmdm.whatsdam.model.mensajesEnviados

/**
 * Clase `MissatgesViewModel` que actúa como el ViewModel para la gestión de mensajes en la aplicación.
 *
 * @constructor Crea una instancia de MissatgesViewModel.
 * @param application La aplicación a la que está asociado el ViewModel.
 */
class MissatgesViewModel(application: Application) : AndroidViewModel(application) {
    // LiveData que almacena el adaptador para el RecyclerView
    private val _adaptador = MutableLiveData<MyAdapter>().apply {
        value = MyAdapter(
            mensajesEnviados
        ) { m: Mensaje, v: View -> MissatgeLongClickedManager(m, v) }
    }
        val adaptador:MutableLiveData<MyAdapter> =_adaptador

    /**
     * Agrega un mensaje al repositorio y notifica al adaptador del RecyclerView para reflejar el cambio.
     *
     * @param m El mensaje a agregar.
     */
    public fun add(m:Mensaje){
        if (MissatgesRepository.getInstance().add(m)){

            // Si se ha agregado un elemento al repositorio de mensajes,
            // notifica al adaptador del RecyclerView para que inserte el mensaje
            // en la última posición.
            adaptador.value?.notifyItemInserted(MissatgesRepository.getInstance().getNumMissatges()-1)

        }
    }

    /**
     * Gestiona la acción cuando se realiza un clic largo en un mensaje.
     *
     * @param contacte El mensaje en el que se ha realizado el clic largo.
     * @param v La vista en la que se ha realizado la acción.
     * @return Verdadero si se ha completado la acción y se detiene la propagación de eventos.
     */
    public fun MissatgeLongClickedManager(contacte:Mensaje, v: View):Boolean {

        // Invoca el método `remove` del repositorio de mensajes para eliminar el mensaje
        val index = MissatgesRepository.getInstance().remove(contacte)

        // Notifica al adaptador del RecyclerView que se ha eliminado un elemento en la posición `index`.
        adaptador.value?.notifyItemRemoved(index)
        return true
    }

}