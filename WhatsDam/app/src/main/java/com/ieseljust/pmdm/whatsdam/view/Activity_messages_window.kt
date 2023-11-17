package com.ieseljust.pmdm.whatsdam.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.ieseljust.pmdm.whatsdam.ViewModels.MessagesViewModel
import com.ieseljust.pmdm.whatsdam.ViewModels.MyAdapter
import com.ieseljust.pmdm.whatsdam.databinding.ActivityMessagesWindowBinding
import com.ieseljust.pmdm.whatsdam.model.Mensaje
import com.ieseljust.pmdm.whatsdam.repository.MissatgesRepository
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Esta es la actividad de la ventana de mensajes.
 * Muestra la información de conexión y permite enviar mensajes.
 */
class Activity_messages_window : AppCompatActivity() {

    // Propiedad para acceder a las vistas del diseño XML mediante ViewBinding
    private lateinit var binding: ActivityMessagesWindowBinding
    private lateinit var viewModel: MessagesViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la vista utilizando el enlace generado por ViewBinding.
        binding = ActivityMessagesWindowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = MessagesViewModel(application) // Crear una instancia de MissatgesViewModel

        // Obtiene referencias a las vistas de la interfaz de usuario
        val messageText = binding.MessageText
        val textView = binding.connectionInfoTextView
        val sendMessage = binding.sendMessage

        // Configurar el RecyclerView
        val recyclerView = binding.MessagesRecyclerView

        // Asociar el LayoutManager al RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Crear e inicializar tu adaptador (MyAdapter) y asignarlo al RecyclerView
        val adapter = MyAdapter(MissatgesRepository.getInstance()) { m: Mensaje, v: View ->
            viewModel.MissatgeLongClickedManager(m, v)
        }
        recyclerView.adapter = adapter

        // Indicamos que el tamaño sea fijo
        recyclerView.setHasFixedSize(true)

        // Obtiene los valores de "NICKNAME_KEY" e "IPSERVER" del ViewModel
        val nickname = viewModel.getUserName()
        val ipserver = viewModel.getServer()

        // Actualiza el texto de la información de conexión
        textView.text = "Conectat a $ipserver com a $nickname"

        // Configura el clic del botón "Send Message"
        // Limpia el texto del campo de mensaje
        sendMessage.setOnClickListener {
            val horaActual = LocalTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val hora = horaActual.format(formatter)

            // Crear un nuevo objeto de Mensaje y agregarlo a la lista mensajesEnviados.
            viewModel.addMessage(Mensaje(nickname, messageText.text.toString(), hora))

            // Limpia el texto del campo de mensaje
            messageText.text.clear()
        }

        // Observa cambios en la lista de mensajes y actualiza el adaptador
        viewModel.messageList.observe(this) { missatges ->
            // Notificamos al adaptador del RecyclerView que la lista de mensajes ha cambiado

            // Desplazamos la vista del RecyclerView hacia la última posición de la lista
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        }
    }
}
