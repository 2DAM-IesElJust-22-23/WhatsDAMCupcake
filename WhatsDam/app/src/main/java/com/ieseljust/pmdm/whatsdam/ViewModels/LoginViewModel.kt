package com.ieseljust.pmdm.whatsdam.ViewModels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ieseljust.pmdm.whatsdam.repository.MissatgesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import com.google.gson.Gson
class LoginViewModel : ViewModel() {

    // Propiedades LiveData para gestionar el estado de la conexión
    private val _loginStatus = MutableLiveData<JSONObject>()
    val loginStatus get() = _loginStatus

    // Método para realizar el login
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun login(username: String, server: String) {
        try {
            // Invocamos al método de Login del repositorio, proporcionándole las credenciales del usuario
            val result = MissatgesRepository.getInstance().login(username, server)

            // Actualizamos el estado de la conexión en el hilo principal utilizando postValue
            _loginStatus.postValue(result)
        } catch (e: Exception) {
            // Manejar la excepción según tus necesidades
            e.printStackTrace()
            // En caso de error, actualizamos el estado de la conexión con un objeto JSON de error
            _loginStatus.postValue(JSONObject().apply {
                put("status", "error")
            })
        }
    }
}