package com.ieseljust.pmdm.whatsdam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ieseljust.pmdm.whatsdam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Restaurar el valor del contador si existe un estado guardado
        if (savedInstanceState != null) {

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
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }
}