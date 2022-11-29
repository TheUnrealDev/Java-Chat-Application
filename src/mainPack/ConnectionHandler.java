package mainPack;

import Commands.CommandHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private final Server server;
    private final Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private String screenName;

    public ConnectionHandler(Server server, Socket client) {
        this.server = server;
        this.client = client;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            NamingHandler namingHandler = new NamingHandler(server, this);
            CommandHandler commandHandler = new CommandHandler(server, this);

            out.println("Successfully connected to the server.");

            screenName = namingHandler.getValidName();
            server.broadcast(screenName + " connected!");
            server.log(screenName + " connected!");

            String message;

            while (true) {
                message = in.readLine();
                if (message == null) { break; }

                boolean isCommandProcessed = commandHandler.ProcessCommands(message);
                if (!isCommandProcessed) {
                    server.broadcast(screenName + ": " + message);
                }
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown() {
        if (!client.isClosed()) {
            String disconnectInfo = screenName + " disconnected!";
            System.out.println(disconnectInfo);
            server.broadcast(disconnectInfo);
            server.removeClient(this);
        }

        try {
            in.close();
            out.close();
            if (client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedReader getIn() {
        return in;
    }
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
    public String getScreenName() {
        return screenName;
    }
}
