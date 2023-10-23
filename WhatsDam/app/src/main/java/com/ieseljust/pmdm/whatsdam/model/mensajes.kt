package com.ieseljust.pmdm.whatsdam.model

// Lista que usaremos para almacenar los mensajes que enviamos

object mensajesEnviados{
    private val mensajesEnviados = mutableListOf<Mensaje>()

    fun getMensajes() = mensajesEnviados

    fun getUltimoNum()= mensajesEnviados.size-1

    fun getNumeroMensajes() = mensajesEnviados.size

    fun remove(m:Mensaje) = mensajesEnviados.remove(m)

    fun add(m:Mensaje) = mensajesEnviados.add(m)

}



