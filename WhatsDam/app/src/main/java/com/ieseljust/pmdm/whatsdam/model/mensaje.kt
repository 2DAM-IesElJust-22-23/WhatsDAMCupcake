package com.ieseljust.pmdm.whatsdam.model

import org.json.JSONObject

// Clase que representa el mensaje, contendra el usuario que ha enviado el mensaje, el texto del mensaje y la hora del envío
data class Mensaje(val usuario: String, val mensaje: String, val hora: String) {

    fun toJSONCommand(): JSONObject {
        val json = JSONObject()
        json.put("command", "newMessage")
        json.put("user", usuario)
        json.put("content", mensaje)
        return json
    }
}
