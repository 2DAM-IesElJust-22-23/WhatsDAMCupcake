package com.ieseljust.pmdm.whatsdam.repository

import com.ieseljust.pmdm.whatsdam.model.Mensaje
import com.ieseljust.pmdm.whatsdam.model.mensajesEnviados

/**
 * Repositorio para gestionar mensajes en la aplicación.
 * Esta clase actúa como un singleton y proporciona métodos para acceder y gestionar mensajes.
 */
class MissatgesRepository private constructor(){

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
    fun add(m: Mensaje) = mensajesEnviados.add(m)

    /**
     * Elimina un mensaje del repositorio.
     *
     * @param m El mensaje a eliminar.
     */
    fun remove(m: Mensaje) = mensajesEnviados.remove(m)

}