package com.ieseljust.pmdm.whatsdam.model

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket

object CommunicationManager {

    var port: Int = 9999
    var server: String = ""
    var listenPort: Int? = null

    suspend fun sendServer(msg: String): JSONObject {
        return withContext(Dispatchers.IO) {
            try {
                Socket().use { socket ->
                    val socketAddr = InetSocketAddress(server, port)
                    socket.connect(socketAddr)

                    // Obtención de los streams de entrada y salida
                    val inputStream = socket.getInputStream()
                    val outputStream = socket.getOutputStream()

                    // Creación de los flujos para la lectura y escritura de caracteres
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val writer = PrintWriter(OutputStreamWriter(outputStream))

                    // Escribimos en el socket el mensaje que queremos proporcionarle al servidor
                    writer.println(msg)
                    writer.flush()

                    // Leemos la respuesta del servidor
                    val responseBuilder = StringBuilder()
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        responseBuilder.append(line)
                    }

                    // Devolvemos la respuesta del servidor en formato JSON
                    JSONObject(responseBuilder.toString())
                }
            } catch (e: IOException) {
                // Manejar la excepción según tus necesidades
                e.printStackTrace()
                JSONObject()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(DelicateCoroutinesApi::class)
    suspend fun prepareListener() {
        listenPort = withContext(Dispatchers.IO) {
            ServerSocket(0).use { it.localPort }
        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                ServerSocket(listenPort!!).use { serverSocket ->
                    while (true) {
                        val clientSocket = serverSocket.accept()

                        GlobalScope.launch(Dispatchers.IO) {
                            try {
                                val inputStream = clientSocket.getInputStream()
                                val outputStream = clientSocket.getOutputStream()

                                val reader = BufferedReader(InputStreamReader(inputStream))
                                val writer = PrintWriter(OutputStreamWriter(outputStream))

                                // Leer la línea recibida a través de los Streams y procesarla
                                val receivedLine = reader.readLine()
                                processNotification(receivedLine)

                                // Responder con el JSON {'status':'ok'}
                                writer.println("{'status':'ok'}")
                                writer.flush()

                                // No cerrar el socket para seguir escuchando más mensajes
                            } catch (e: Exception) {
                                e.printStackTrace()
                            } finally {
                                clientSocket.close()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun processNotification(notification: String?) {
        try {
            if (notification != null) {
                val jsonNotification = JSONObject(notification)

                // Verificar el tipo del mensaje
                val messageType = jsonNotification.getString("type")
                when (messageType) {
                    "message" -> {
                        // Procesar el mensaje si el tipo es "message"
                        val user = jsonNotification.getString("user")
                        val content = jsonNotification.getString("content")
                        mensajesEnviados.add(user,content)
                    }
                }

            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun login(user: String, password: String): JSONObject {
        return withContext(Dispatchers.IO) {
            try {
                // Preparar el puerto para escuchar
                prepareListener()

                // Enviar mensaje de registro al servidor
                val registerMsg = JSONObject().apply {
                    put("command", "register")
                    put("user", user)
                    put("listenPort", listenPort)
                }

                val response = sendServer(registerMsg.toString())

                // Devolver la respuesta del servidor
                response
            } catch (e: Exception) {
                // Manejar la excepción según tus necesidades
                e.printStackTrace()
                JSONObject().apply {
                    put("status", "error")
                }
            }
        }
    }

}
