package com.ieseljust.pmdm.whatsdam.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalTime
import java.time.format.DateTimeFormatter
// Lista que usaremos para almacenar los mensajes que enviamos

object mensajesEnviados{
    private val _messages = MutableLiveData<ArrayList<Mensaje>>().apply{
        value= ArrayList<Mensaje>()
    }

    val mensajesEnviados: MutableLiveData<ArrayList<Mensaje>> = _messages

    /**
     *  Obtiene todos los mensajes enviados.
     *
     *  @return: Lista de mensajes enviados.
     */
    fun getMensajes() = mensajesEnviados

    /**
     * Obtiene un mensaje específico en la posición dada.
     *
     * @param posicion La posición del mensaje en la lista.
     * @return El mensaje en la posición especificada.g
     */
    fun getMensaje(position: Int): Mensaje {
        val currentMessages = _messages.value ?: throw IndexOutOfBoundsException("Position $position out of bounds")
        return currentMessages.getOrNull(position)
            ?: throw IndexOutOfBoundsException("Position $position out of bounds")
    }

    /**
     * Obtiene el índice del último mensaje en la lista.
     *
     * @return El índice del último mensaje.
     */
    fun getUltimoNum(): Int {
        return if (mensajesEnviados.value?.isNotEmpty() == true) {
            mensajesEnviados.value!!.size - 1
        } else {
            -1
        }
    }

    /**
     * Obtiene el número total de mensajes enviados.
     *
     * @return El número de mensajes en la lista.
     */
    fun getNumeroMensajes(): Int {
        return mensajesEnviados.value?.size ?: 0
    }

    /**
     * Elimina un mensaje de la lista de mensajes enviados.
     *
     * @param m El mensaje a eliminar.
     */
    fun remove(m: Mensaje) {
        val currentMessages = mensajesEnviados.value ?: return
        currentMessages.remove(m)
        _messages.value = currentMessages
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun add(username: String, text: String) {
        val horaActual = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val hora = horaActual.format(formatter)

        val newMessage = Mensaje(username, text, hora)
        val currentMessages = _messages.value ?: ArrayList()
        currentMessages.add(newMessage)
        _messages.value = currentMessages
    }

    suspend fun sendMessage(message: Mensaje) {
        val jsonMessage = JSONObject()
        jsonMessage.put("user", message.usuario)
        jsonMessage.put("content", message.mensaje)

        // Enviar el mensaje al servidor y recibir la respuesta en un bloque de corrutina
        withContext(Dispatchers.IO) {
            try {
                CommunicationManager.sendServer(jsonMessage.toString())
            } catch (e: Exception) {
                // Manejar la excepción si ocurre algún error durante la comunicación con el servidor
                // Puedes imprimir el mensaje o tomar otras medidas según tu lógica de manejo de errores
                e.printStackTrace()
            }
        }
    }


}



