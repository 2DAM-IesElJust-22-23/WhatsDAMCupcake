package com.ieseljust.pmdm.whatsdam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ieseljust.pmdm.whatsdam.databinding.ActivityMessagesWindowBinding


/**
 * Esta es la actividad de la ventana de mensajes.
 * Muestra la información de conexión y permite enviar mensajes.
 */
class Activity_messages_window : AppCompatActivity() {

    private lateinit var binding: ActivityMessagesWindowBinding
    data class mensajes(val usuario: String, val mensaje: String, val hora: java.time.Clock)
    val mensajes_enviados = mutableListOf<mensajes>()

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
            messageText.text.clear()
        }
    }
}

