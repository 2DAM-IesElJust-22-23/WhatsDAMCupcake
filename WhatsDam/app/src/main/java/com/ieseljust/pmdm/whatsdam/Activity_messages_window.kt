package com.ieseljust.pmdm.whatsdam

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.ieseljust.pmdm.whatsdam.databinding.ActivityMessagesWindowBinding
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date


/**
 * Esta es la actividad de la ventana de mensajes.
 * Muestra la información de conexión y permite enviar mensajes.
 */
class Activity_messages_window : AppCompatActivity() {

    private lateinit var binding: ActivityMessagesWindowBinding
    data class mensajes(val usuario: String, val mensaje: String, val hora: String)
    val mensajes_enviados = mutableListOf<mensajes>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la vista utilizando el enlace generado por ViewBinding.
        binding = ActivityMessagesWindowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtiene referencias a las vistas de la interfaz de usuario
        val messageText = binding.MessageText
        val textView = binding.connectionInfoTextView
        val sendMessage = binding.sendMessage

        // Obtiene los valores de "NICKNAME_KEY" e "IPSERVER" del Intent
        val nickname = intent.getStringExtra("NICKNAME_KEY")
        val ipserver = intent.getStringExtra("IPSERVER")

        // Actualiza el texto de la información de conexión
        textView.text = "Conectat a $ipserver com a $nickname"

        // Configura el clic del botón "Send Message"
        // Limpia el texto del campo de mensaje
        sendMessage.setOnClickListener {
            val horaActual = LocalTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val hora = horaActual.format(formatter)

            mensajes_enviados.add(mensajes(nickname.toString(),messageText.text.toString(),hora));

            messageText.text.clear()
        }
    }
}

