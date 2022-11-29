package mainPack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
    private Socket client;
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

            InputHandler inHandler = new InputHandler();
            Thread t = new Thread(inHandler);
            t.start();

            String inMessage;
            while ((inMessage = in.readLine()) != null) {
                System.out.println(inMessage);
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
            if (!client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    class InputHandler implements  Runnable {
        @Override
        public void run() {
            try {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                while (!isClosed) {
                    String message = inputReader.readLine();
                    System.out.println("");
                    out.println(message);
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
