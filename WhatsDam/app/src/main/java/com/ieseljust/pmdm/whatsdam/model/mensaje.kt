package com.ieseljust.pmdm.whatsdam.model

// Clase que representa el mensaje, contendra el usuario que ha enviado el mensaje, el texto del mensaje y la hora del envío
data class Mensaje(val usuario: String, val mensaje: String, val hora: String)

// Lista que usaremos para almacenar los mensajes que enviamos
val mensajesEnviados = mutableListOf<Mensaje>()