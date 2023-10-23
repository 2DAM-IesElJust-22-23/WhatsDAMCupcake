package com.ieseljust.pmdm.whatsdam.repository

import com.ieseljust.pmdm.whatsdam.model.Mensaje
import com.ieseljust.pmdm.whatsdam.model.mensajesEnviados
class MissatgesRepository {
    fun getMissatges(): List<Mensaje?>? {
        return mensajesEnviados
    }

    fun getQuantitatMissatges(): Int {
        return mensajesEnviados.size
    }

    fun afegirMissatge(missatge: Mensaje) {
        mensajesEnviados.add(missatge)
    }
}