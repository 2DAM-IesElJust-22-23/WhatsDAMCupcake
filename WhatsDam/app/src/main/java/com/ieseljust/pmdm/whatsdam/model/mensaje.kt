package com.ieseljust.pmdm.whatsdam.model

// Clase que representa el mensaje, contendra el usuario que ha enviado el mensaje, el texto del mensaje y la hora del envío
data class Mensaje(val usuario: String, val mensaje: String, val hora: String)
