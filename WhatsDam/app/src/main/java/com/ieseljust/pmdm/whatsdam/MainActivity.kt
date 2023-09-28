package com.ieseljust.pmdm.whatsdam

import android.content.Intent
import android.net.InetAddresses.isNumericAddress
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        // Restaurar el valor del contador si existe un estado guardado
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
                startActivity(intent)
            }
        }

    }
    override fun onStart() {
        super.onStart()
        // Registro de log en onStart
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        // Registro de log en onResume
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        // Registro de log en onPause
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        // Registro de log en onStop
        Log.d(TAG, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        // Registro de log en onRestart
        Log.d(TAG, "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        // Registro de log en onDestroy
        Log.d(TAG, "onDestroy")
    }

    companion object {
        private const val TAG = "MainActivity"
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