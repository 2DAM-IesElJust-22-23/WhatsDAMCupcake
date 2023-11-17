package com.ieseljust.pmdm.whatsdam.ViewModels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ieseljust.pmdm.whatsdam.model.Mensaje
import com.ieseljust.pmdm.whatsdam.repository.MissatgesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MessagesViewModel(application: Application) : AndroidViewModel(application) {

    // Referencia al repositorio
    private val repository = MissatgesRepository.getInstance()

    // Una lista de mensajes, definida como LiveData, que enlaza con la lista de mensajes proporcionada por el repositorio.
    val messageList: LiveData<ArrayList<Mensaje>> by lazy {
        repository.getMissatges() ?: MutableLiveData<ArrayList<Mensaje>>().apply { value = ArrayList() }
    }

    // Método para agregar un mensaje, que se ejecutará en un hilo diferente.
    fun addMessage(msg: Mensaje) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                repository.sendMessage(msg.mensaje)
            }
            // Puedes manejar el resultado si es necesario
        }
    }

    // Método para obtener el nombre de usuario del repositorio.
    fun getUserName(): String {
        return repository.username
    }

    // Método para obtener el servidor del repositorio.
    fun getServer(): String {
        return repository.server
    }

    public fun MissatgeLongClickedManager(contacte:Mensaje, v: View):Boolean {
        // LiveData que almacena el adaptador para el RecyclerView
        val _adaptador = MutableLiveData<MyAdapter>().apply {
            value = MyAdapter(
                MissatgesRepository.getInstance()
            ) { m: Mensaje, v: View -> MissatgeLongClickedManager(m, v) }
        }
        val adaptador:MutableLiveData<MyAdapter> =_adaptador
        // Invoca el método `remove` del repositorio de mensajes para eliminar el mensaje
        val index = MissatgesRepository.getInstance().remove(contacte)

        // Notifica al adaptador del RecyclerView que se ha eliminado un elemento en la posición `index`.
        adaptador.value?.notifyItemRemoved(index)
        return true
    }
}