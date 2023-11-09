package com.ieseljust.psp.client.communications;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.ieseljust.psp.client.CurrentConfig;
import com.ieseljust.psp.client.Message;

import org.json.JSONException;
import org.json.JSONObject;

public class communicationManager {

    /* Aquesta classe s'encarrega de la gestió de la
     comunicació amb el servidor.
     */
    
    public static JSONObject sendServer(String msg) {

        // TO-DO:
        // Envía al servidor l'string msg
        // I retorna un JSON amb la resposta

        try{
            // Establece la conexión
            Socket socket = new Socket(CurrentConfig.server(), CurrentConfig.port());

            // Envía el mensaje
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            out.println(msg);
            
            // Lee la respuesta del servidor
            BufferedReader in = new BufferedReader((new InputStreamReader(socket.getInputStream())));
            String respuesta = in.readLine();

            // Cierra la conexion
            socket.close();

            // Crea un objeto JSON con la respuesta del servidor
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("respuesta", respuesta);

            return jsonResponse;

        }catch(Exception e){
            System.out.println("Error: " + e);
        }

        return null;
        
    }

    public static void connect() throws JSONException, communicationManagerException {

        // TO-DO:

        // Creem un misstge pe al servidor amb l'ordre (command) register, 
        // el nom d'usuari (user) i el port (listenPost) pel qual el client escoltarà 
        // les notificacions (el tenim a CurrentConfig.listenPort())

        // Enviarà el missatge al servidor a través de sendServer.

        // Si es produeix un error, llançarà una excepció i aturarà
        // l'aplicaió (per exemple, si l'usuari ja existeix al servidor)
        // Teniu per a això l'excepció communicationManagerException 
        // com a excepció personalitzada al projecte.

        try{
            // Establece la conexion
            Socket socket = new Socket(CurrentConfig.server(), CurrentConfig.port());

            // Envia el mensaje al servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

            // Lee la respuesta del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();

            // Cierra la conexión
            socket.close();

            if(response.equals("ERROR")){
                throw new communicationManagerException("Error al entrar en el servidor");
            }
        }catch(Exception e){
            System.out.println("Error: " + e);
        }
        


    }

    public static void sendMessage(Message m){
        // Envia un misstge al servidor (es fa amb una línia!)
    }    
}
