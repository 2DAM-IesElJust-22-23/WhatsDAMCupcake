package com.ieseljust.pmdm.whatsdam.repository

import com.ieseljust.pmdm.whatsdam.model.Mensaje
import com.ieseljust.pmdm.whatsdam.model.mensajesEnviados
class MissatgesRepository private constructor(){

    companion object {
        private var INSTANCE: MissatgesRepository? = null

        fun getInstance(): MissatgesRepository {
            if (INSTANCE == null) {
                INSTANCE = MissatgesRepository()
            }
            return INSTANCE!!
        }
    }

    fun getMissatges() = mensajesEnviados.getMensajes()
    fun getNumMissatges() = mensajesEnviados.getNumeroMensajes()
    fun add(m:Mensaje) = mensajesEnviados.add(m)
    fun remove(m:Mensaje) = mensajesEnviados.remove(m)
}