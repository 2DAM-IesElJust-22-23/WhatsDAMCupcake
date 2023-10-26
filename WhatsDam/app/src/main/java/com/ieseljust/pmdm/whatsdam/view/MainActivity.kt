package com.ieseljust.pmdm.whatsdam.view

import android.content.Intent
import android.net.InetAddresses.isNumericAddress
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.ieseljust.pmdm.whatsdam.ViewModels.MissatgesViewModel
import com.ieseljust.pmdm.whatsdam.databinding.ActivityMainBinding
import com.ieseljust.pmdm.whatsdam.model.Mensaje

/**
 * Esta es la clase principal de la aplicación Android.
 * Extiende de AppCompatActivity, que es una clase base para las actividades de Android.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // Variable para vincular la vista XML a esta actividad
    private var nickname="" // Almacena el nombre del usuario
    private var ipserver = "" // Almacena la ip del servidor

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {

        // Inicializa la vista utilizando el enlace generado por ViewBinding.
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Restaurar el valor si existe un estado guardado
        if (savedInstanceState != null) {
            nickname = savedInstanceState.getString("nickname", "")
            ipserver = savedInstanceState.getString("ipserver", "")
        }

        // Configura una función para cuando se haga clic en btConnect
        binding.buttonConnect.setOnClickListener {
            // Crear un Intent para ir a la actividad de destino
            val intent = Intent(this, Activity_messages_window::class.java)

            // Obtiene los datos la vista.
            nickname = binding.nickNameText.text.toString()
            ipserver = binding.serverAddressText.text.toString()

            // Iniciar la actividad de destino si el formato es correcto
            if((nickname.isNotEmpty()) and isNumericAddress(ipserver)){
                intent.putExtra("NICKNAME_KEY", nickname)
                intent.putExtra("IPSERVER", ipserver)
                startActivity(intent)
            }
        }

    }

    // Guarda el estado de la actividad
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("nickname", nickname)
        outState.putString("ipserver", ipserver)
    }


}