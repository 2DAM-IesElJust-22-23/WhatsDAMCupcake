package com.ieseljust.pmdm.whatsdam.ViewModels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ieseljust.pmdm.whatsdam.model.Mensaje
import com.ieseljust.pmdm.whatsdam.repository.MissatgesRepository

/**
 * Clase `MissatgesViewModel` que actúa como el ViewModel para la gestión de mensajes en la aplicación.
 *
 * @constructor Crea una instancia de MissatgesViewModel.
 * @param application La aplicación a la que está asociado el ViewModel.
 */
class MissatgesViewModel(application: Application) : AndroidViewModel(application) {

    // LiveData que almacena el adaptador para el RecyclerView
    private val _adaptador = MutableLiveData<MyAdapter>().apply {
        value = MyAdapter()
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

}