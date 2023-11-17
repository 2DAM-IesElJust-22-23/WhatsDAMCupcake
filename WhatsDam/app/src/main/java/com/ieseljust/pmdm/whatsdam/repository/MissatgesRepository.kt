package com.ieseljust.pmdm.whatsdam.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.ieseljust.pmdm.whatsdam.model.Mensaje
import com.ieseljust.pmdm.whatsdam.model.mensajesEnviados
import com.ieseljust.pmdm.whatsdam.model.CommunicationManager
import org.json.JSONObject

/**
 * Repositorio para gestionar mensajes en la aplicación.
 * Esta clase actúa como un singleton y proporciona métodos para acceder y gestionar mensajes.
 */
class MissatgesRepository private constructor(){

    var username:String=""
    var server:String=""

    companion object {
        private var INSTANCE: MissatgesRepository? = null

        /**
         * Obtiene una instancia única del repositorio de mensajes.
         *
         * @return Instancia del repositorio de mensajes.
         */
        fun getInstance(): MissatgesRepository {
            if (INSTANCE == null) {
                INSTANCE = MissatgesRepository()
            }
            return INSTANCE!!
        }
    }

    /**
     * Obtiene todos los mensajes almacenados en el repositorio.
     *
     * @return Lista de mensajes almacenados.
     */
    fun getMissatges() = mensajesEnviados.getMensajes()

    /**
     * Obtiene el número total de mensajes almacenados en el repositorio.
     *
     * @return Número de mensajes almacenados.
     */
    fun getNumMissatges() = mensajesEnviados.getNumeroMensajes()

    /**
     * Agrega un nuevo mensaje al repositorio.
     *
     * @param m El mensaje a agregar.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun add(m: Mensaje): Boolean{
        mensajesEnviados.add(m.usuario,m.mensaje)
        return true
    }

    /**
     * Elimina un mensaje del repositorio.
     *
     * @param m El mensaje a eliminar.
     */
    fun remove(m: Mensaje) = mensajesEnviados.remove(m)

    /**
     * Envía un mensaje al servidor.
     *
     * @param msg El mensaje a enviar.
     */
    suspend fun sendMessage(msg: String) {
        CommunicationManager.sendServer(msg)
    }

    /**
     * Realiza el login y actualiza las propiedades de usuario y servidor en la clase.
     *
     * @param username El nombre de usuario.
     * @param server El servidor al que conectarse.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun login(username: String, server: String): JSONObject {
        this.username = username
        this.server = server
        return CommunicationManager.login(username, server)
    }

    /**
     * Obtiene el mensaje en la posición indicada del repositorio.
     *
     * @param position La posición del mensaje.
     * @return El mensaje en la posición indicada.
     */
    fun getMessage(position: Int): Mensaje {
        return mensajesEnviados.getMensaje(position)
    }
}

