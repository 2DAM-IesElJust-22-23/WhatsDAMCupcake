package com.ieseljust.psp.client.communications;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.ieseljust.psp.client.CurrentConfig;
import com.ieseljust.psp.client.Message;
import com.ieseljust.psp.client.ViewModel;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

import org.json.JSONObject;
import org.json.JSONArray;

public class serverListener implements Runnable {

    /*
     * Aquesta classe s'encarrega de gestionar els broadcasts que fa el servidor
     * cap als clients subscrits a les seues publicacions.
     * Implementarà doncs un servei que escoltarà en un port aleatori el que
     * li envia el servidor de missatgeria i ho processarà de forma adeqüada.
     * 
     */

    ViewModel vm;

    public serverListener(ViewModel vm) {
        this.vm = vm;
    }

    int listenerPort = CurrentConfig.listenPort();

    @Override
    public void run() {
    // Resto del código antes de la implementación del bucle

    try {

        while (true) {

            ServerSocket serSocket = new ServerSocket(listenerPort);

            // 2. Iniciamos un bucle infinito a la espera de recibir conexiones
            Socket socket = serSocket.accept(); // Espera a que llegue una conexión

            // Procesamos la conexión en un hilo aparte
            new Thread(() -> {
                try {
                    // Aquí procesamos la entrada
                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                    String messageString;
                    StringBuilder sb = new StringBuilder();

                    while ((messageString = reader.readLine()) != null) {
                        sb.append(messageString);
                    }

                    // Una vez que hemos recibido el mensaje completo
                    String receivedMessage = sb.toString();

                    // Parseamos el mensaje como JSON
                    JSONObject jsonMessage = new JSONObject(receivedMessage);

                    // Identificamos el tipo de mensaje
                    String messageType = jsonMessage.getString("type");

                    if (messageType.equals("userlist")) {
                        // Mensaje tipo lista de usuarios
                        JSONArray userList = jsonMessage.getJSONArray("content");
                        // Procesar la lista de usuarios
                        // Hacer lo que sea necesario con la lista de usuarios
                    } else if (messageType.equals("message")) {
                        // Mensaje tipo mensaje de usuario
                        String username = jsonMessage.getString("user");
                        String content = jsonMessage.getString("content");
                        // Procesar el mensaje del usuario
                        // Hacer lo que sea necesario con el mensaje
                    } else {
                        System.out.println("Mensaje de tipo desconocido: " + messageType);
                    }

                    // Cerramos el socket después de procesar el mensaje
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    } catch (IOException e) {
        System.out.println("Error al establecer la conexión: " + e.getMessage());
    }
}


}
