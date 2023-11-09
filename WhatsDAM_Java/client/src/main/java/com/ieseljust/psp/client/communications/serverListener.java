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

        // TO-DO
        // 2. Iniciem un bucle infinit a l'espera de rebre connexions
        // Quan arribe una connexió la processrem de manera adeqüada
        // Les peticions que podme rebre seran de tipus:
        // {"type": "userlist", "content": [Llista d'usuaris]}, amb un JSONArray amb la llista d'usuaris.
        // {"type": "message", "user":"usuari", "content": "Contingut del missatge" }
        // És interessant implementar un mètode a banda per processat aquestes línies
        // però no cal que creem un fil a propòsit per atendre cada missatge, ja que
        // no som un servidor com a tal, i el que estem fent aci, és mantindre un 
        // canal de recepció només amb el servidor.
        ServerSocket serSocket = null;
        try {
            serSocket = new ServerSocket(listenerPort);
            CurrentConfig.setlistenPort(serSocket.getLocalPort());
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        try {

            while (true) {

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
                            ArrayList<String> combinedList = new ArrayList<>();

                            for (int i = 0; i < userList.length(); i++) {
                                combinedList.add(userList.getString(i));
                            }

                            combinedList.addAll(vm.getLlistaUsuaris());
                            // Hacer lo que sea necesario con la lista de usuarios
                            vm.updateUserList(combinedList);
                        } else if (messageType.equals("message")) {
                            // Mensaje tipo mensaje de usuario
                            String username = jsonMessage.getString("user");
                            String content = jsonMessage.getString("content");
                            // Procesar el mensaje del usuario
                            Message mensaje = new Message(username, content);
                            // Hacer lo que sea necesario con el mensaje
                            vm.addMessage(mensaje);
                        } else {
                            System.out.println("Mensaje de tipo desconocido: " + messageType);
                        }

                        // Cerramos el socket después de procesar el mensaje
                        socket.close();
                    } catch (IOException e) {
                        System.out.println("Error: " + e);
                    }
                }).start();
            }

        } catch (IOException e) {
            System.out.println("Error al establecer la conexión: " + e.getMessage());
        }
    }

}
