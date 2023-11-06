package com.ieseljust.psp.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

import org.json.JSONObject;

class MsgHandler implements Runnable {
    // Classe per atendre les peticions a través de threads

    private Socket MySocket; // Socket que atendrà la petició
    private ArrayList<Connexio> Connexions; // Vector de connexions del servidor

    MsgHandler(Socket socket, ArrayList<Connexio> connexions) {
        // Constructor de la classe.
        // S'inicia amb un socket i la llista de connexions.
        // Aquesta llista de connexions només podrà ser modificada
        // per un fil en la seua secció crítica

        MySocket = socket;
        Connexions = connexions;
    }

    JSONObject sendMessage(JSONObject MissatgeRebut) {
        // Rep un missatge en format JSON i l'envia a través 
        // de la classe Notifier a tots els usuaris connectats
        // fent ús del mètode broadCastMessage. 
        // Retornarà un JSONObject amb la resposta que ens
        // retorne aquest mètode.

        JSONObject resposta = new JSONObject();

        try {
            Notifier.broadCastMessage(MissatgeRebut);
            resposta.put("status", "ok");

        } catch (Exception e) {
            resposta.put("error", e.getMessage());
        }
        return resposta;

    }

    JSONObject registerUser(JSONObject MissatgeRebut) {
        // Mètode per registrar l'usuari

        JSONObject resposta = new JSONObject();

        // Recorre totes les connexions existents, i comprova si existeix
        // un usuari amb el mateix nom.
        
        for (Connexio connexio : Connexions) {
            System.out.println(connexio.toString());  
            if (MissatgeRebut.getString("user").equals(connexio.getUser())) {
                // Si hi ha un usuari amb el mateix nom, retorna un missatge d'error
                resposta.put("status", "error");
                resposta.put("message", "User " + connexio.getUser() + " already registered");
                return resposta;
            }
        }

        // En cas que no existisca, crea una nova connexió amb l'usuari
        synchronized (Connexions) {
            Connexio con = new Connexio(MissatgeRebut.getString("user"), MySocket, MissatgeRebut.getInt("listenPort"));
            Connexions.add(con);
        }

        resposta.put("status", "ok");

        // I com que s'ha fet una actualizació de l'estat al
        // servidor, enviem un broadcast a tots els clients
        // amb la llista d'usuaris (a través del mètode broadCastUsers)
        Notifier.broadCastUsers();

        return resposta;
    }


    @Override
    public void run() {
        // Mètode que s'encarrega d'executar el fil

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(MySocket.getInputStream()));
            PrintWriter writer = new PrintWriter(MySocket.getOutputStream(), true);

            // Llegir la línia enviada pel client
            String clientRequest = reader.readLine();

            // Convertir la línia llegida en un objecte JSON
            JSONObject jsonRequest = new JSONObject(clientRequest);

            // Obtindre la comanda enviada pel client
            String command = jsonRequest.getString("command");

            // Manejar diferents comandes
            switch (command) {
                case "register":
                    // Trucar al mètode RegisterUser i obtenir la resposta
                    JSONObject registerResponse = registerUser(jsonRequest);

                    // Enviar la resposta de volta al client
                    writer.println(registerResponse.toString());
                    break;

                case "newMessage":
                    // Trucar al mètode sendMessage i obtenir la resposta
                    JSONObject messageResponse = sendMessage(jsonRequest);

                    // Enviar la resposta de volta al client
                    writer.println(messageResponse.toString());
                    break;

                default:
                    // Comanda no vàlida
                    JSONObject errorResponse = new JSONObject();
                    errorResponse.put("status", "error");
                    errorResponse.put("error", "Comanda invàlida");
                    writer.println(errorResponse.toString());
                    break;
            }

            MySocket.close(); // Tancar el socket després de respondre
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

        

    

