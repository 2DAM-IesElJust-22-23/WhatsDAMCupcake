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
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class communicationManager {

    /* Aquesta classe s'encarrega de la gestiÃ³ de la
     comunicaciÃ³ amb el servidor.
     */
    public static JSONObject sendServer(String msg) {

        try {
            // Establece la conexión
            Socket socket = new Socket(CurrentConfig.server(), CurrentConfig.port());

            // Envía el mensaje
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(msg);

            // Lee la respuesta del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder respuestaBuilder = new StringBuilder();
            String linea;

            while ((linea = in.readLine()) != null) {
                respuestaBuilder.append(linea);
            }

            // Cierra la conexión
            socket.close();

            // Crea un objeto JSON con la respuesta del servidor
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("respuesta", respuestaBuilder.toString());

            return jsonResponse;

        } catch (IOException | JSONException e) {
            System.out.println("Error: " + e);
        }

        return null;
    }

    public static void connect() throws JSONException, communicationManagerException {
        // Obtener la informacion de configuracion del servidor desde CurrentConfig
        String username = CurrentConfig.username();
        int listenPort = CurrentConfig.listenPort();

        // Crear el mensaje JSON con la orden "register", el nombre de usuario y el puerto de escucha
        JSONObject registerMessage = new JSONObject();
        registerMessage.put("command", "register");
        registerMessage.put("user", username);
        registerMessage.put("listenPort", listenPort);

        try {
            // Enviar el mensaje al servidor y recibir la respuesta
            JSONObject response = sendServer(registerMessage.toString());

            // Verificar la respuesta del servidor
            if (response.has("status") && response.getString("status").equals("error")) {
                // Si el servidor devuelve un estado de error, lanzar una excepcion personalizada
                throw new communicationManagerException(response.getString("message"));
            }

        } catch (communicationManagerException | JSONException e) {
            System.out.println("Error: " + e);
            // Manejar la excepcion segun tus necesidades
        }
    }

    public static void sendMessage(Message m) {
        // Envia un misstge al servidor (es fa amb una lÃ­nia!)
    }
}
