package mainPack;

import messages.*;
import userInterface.Gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
    private Socket client;
    private Gui gui;
    private BufferedReader in;
    private PrintWriter out;
    public Client() {}
    @Override
    public void run() {
        try {
            client = new Socket("127.0.0.1", 9999); //Selects the socket at port 9999 for use in server communications.
            out = new PrintWriter(client.getOutputStream(), true); //Creates a new PrintWriter to be used to send data through the socket.
            in = new BufferedReader(new InputStreamReader(client.getInputStream())); //Creates a new BufferedReader to be used to receive data through the socket.
            gui = new Gui(this);

            String inMessage;
            while ((inMessage = in.readLine()) != null) { //Keep the client waiting for new data from the socket as long as there is data to be retrieved.
                DeserializedMessage deserializedMessage = MessageDeserializer.deserializeMessage(inMessage); //Deserializes the Message from the encoded string in order
                // to properly be able to display the information about the message in the Gui.
                gui.addMessage(deserializedMessage);
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown() {
        try {
            out.close();
            in.close();
            gui.closeGui();
            if (!client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}
