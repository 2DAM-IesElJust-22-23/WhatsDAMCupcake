package com.ieseljust.pmdm.whatsdam.model

// Lista que usaremos para almacenar los mensajes que enviamos

object mensajesEnviados{
    private val mensajesEnviados = mutableListOf<Mensaje>()

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
     * @return El mensaje en la posición especificada.
     */
    fun getMensaje(posicion: Int) = mensajesEnviados[posicion]

    /**
     * Obtiene el índice del último mensaje en la lista.
     *
     * @return El índice del último mensaje.
     */
    fun getUltimoNum()= mensajesEnviados.size-1

    /**
     * Obtiene el número total de mensajes enviados.
     *
     * @return El número de mensajes en la lista.
     */
    fun getNumeroMensajes() = mensajesEnviados.size

    /**
     * Elimina un mensaje de la lista de mensajes enviados.
     *
     * @param m El mensaje a eliminar.
     */
    fun remove(m:Mensaje) = mensajesEnviados.remove(m)

    /**
     * Agrega un nuevo mensaje a la lista de mensajes enviados.
     *
     * @return m El mensaje a agregar.
     */
    fun add(m:Mensaje) = mensajesEnviados.add(m)

}



