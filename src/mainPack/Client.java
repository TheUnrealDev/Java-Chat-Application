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
    private boolean isClosed;

    public Client() {
        isClosed = false;
    }

    @Override
    public void run() {
        try {
            client = new Socket("127.0.0.1", 9999);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            gui = new Gui(this);

            InputHandler inHandler = new InputHandler();
            Thread t = new Thread(inHandler);
            t.start();

            String inMessage;
            while ((inMessage = in.readLine()) != null) {
                DeserializedMessage deserializedMessage = MessageDeserializer.deserializeMessage(inMessage);
                gui.addMessage(deserializedMessage.senderName, deserializedMessage.message);
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown() {
        isClosed = true;
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

    class InputHandler implements  Runnable {
        @Override
        public void run() {
            try {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                while (!isClosed) {
                    String message = inputReader.readLine();

                    if (message.equals("/quit")) {
                        inputReader.close();
                        shutdown();
                    }
                }
            } catch (IOException e) {
                //Handle;
            }
        }
    }
    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}
