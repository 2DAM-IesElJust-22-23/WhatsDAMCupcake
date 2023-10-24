package com.ieseljust.pmdm.whatsdam

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.ieseljust.pmdm.whatsdam.ViewModels.MissatgesViewModel
import com.ieseljust.pmdm.whatsdam.databinding.ActivityMessagesWindowBinding
import com.ieseljust.pmdm.whatsdam.model.Mensaje
import com.ieseljust.pmdm.whatsdam.model.mensajesEnviados
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Esta es la actividad de la ventana de mensajes.
 * Muestra la información de conexión y permite enviar mensajes.
 */
class Activity_messages_window : AppCompatActivity() {

    private lateinit var binding: ActivityMessagesWindowBinding

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

        // Configurar el RecyclerView
        val recyclerView = binding.MensajesRecyclerView

        // Asociar el LayoutManager al RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Crear e inicializar tu adaptador (MyAdapter) y asignarlo al RecyclerView

        val adapter = MyAdapter(mensajesEnviados) { m: Mensaje, v: View ->
            val missatgesViewModel = MissatgesViewModel(this.application) // Crea una instancia de MissatgesViewModel
            missatgesViewModel.MissatgeLongClickedManager(m, v) // Llama a la función desde la instancia
        }
        recyclerView.adapter = adapter

        // Indicamos que el tamaño sea fijo
        recyclerView.setHasFixedSize(true)

        // Creamos una instancia de adaptador
        val missatgesViewModel = MissatgesViewModel(this.application)

        recyclerView.adapter = MyAdapter(mensajesEnviados) { m: Mensaje, v: View ->
            missatgesViewModel.MissatgeLongClickedManager(m, v)
        }

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

            // Crear un nuevo objeto de Mensaje y agregarlo a la lista mensajesEnviados.
            mensajesEnviados.add(Mensaje(nickname.toString(),messageText.text.toString(),hora))

            // Notificar al adaptador de MensajesRecyclerView que se ha insertado un nuevo elemento en la lista.
            binding.MensajesRecyclerView.adapter?.notifyItemInserted(mensajesEnviados.getUltimoNum())

            // Desplazar la vista del RecyclerView hacia la última posición de la lista (el mensaje recién agregado).
            recyclerView.scrollToPosition(mensajesEnviados.getUltimoNum())

            messageText.text.clear()
        }
    }
}

