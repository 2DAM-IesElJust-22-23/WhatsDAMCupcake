package com.ieseljust.pmdm.whatsdam

import android.content.Intent
import android.net.InetAddresses.isNumericAddress
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.ieseljust.pmdm.whatsdam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var nickname=""
    private var ipserver = ""
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Restaurar el valor si existe un estado guardado
        if (savedInstanceState != null) {
            nickname = savedInstanceState.getString("nickname", "")
            ipserver = savedInstanceState.getString("ipserver", "")
        }

        val btConnect = binding.buttonConnect

        btConnect.setOnClickListener {
            // Crear un Intent para ir a la actividad de destino
            val intent = Intent(this, Activity_messages_window::class.java)

            nickname = binding.nickNameText.text.toString()
            ipserver = binding.serverAddressText.text.toString()

            // Iniciar la actividad de destino
            if((nickname != "") and isNumericAddress(ipserver)){
                intent.putExtra("NICKNAME_KEY", nickname)
                intent.putExtra("IPSERVER", ipserver)
                startActivity(intent)
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("nickname", nickname)
        outState.putString("ipserver", ipserver)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        nickname = savedInstanceState.getString("nickname", "")
        ipserver = savedInstanceState.getString("ipserver", "")
    }
}