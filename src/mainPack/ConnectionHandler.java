/*
Author: Filip Hellgren

The ConnectionHandler class responsible for handling communications between the server and the client.
 */

package mainPack;

import commands.CommandHandler;
import messages.ClientMessage;
import messages.Message;
import messages.ServerMessage;

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

    public void sendMessage(Message message) {
        String formattedMessage = message.formatMessage(); //Formats the message as a string containing information based on which type of message it is, Server or Client
        //This is done in order to be able to send all the information about the message through the socket.
        out.println(formattedMessage);
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(client.getOutputStream(), true); //Creates a new PrintWriter to be used to send data through the socket.
            in = new BufferedReader(new InputStreamReader(client.getInputStream())); //Creates a new BufferedReader to be used to receive data through the socket.

            NamingHandler namingHandler = new NamingHandler(server, this);
            CommandHandler commandHandler = new CommandHandler(server, this);

            sendMessage(new ServerMessage("Successfully connected to the server."));

            screenName = namingHandler.getValidName(); //Forces the client to choose a screen name upon joining the chat.
            server.broadcast(new ServerMessage(screenName + " connected!"));
            server.log(screenName + " connected!");

            String message;

            while (true) {
                //While there is still input to be read from the BufferedReader keep listening for new data.
                message = in.readLine();
                if (message == null) { break; }

                boolean isCommandProcessed = commandHandler.ProcessCommands(message); //Checks if the input is a valid command and if so returns a boolean containing if the command was processed or not
                //If the input was processed the message should not be printed to other clients.
                if (!isCommandProcessed) {
                    Message newMessage = new ClientMessage(this, message);
                    server.broadcast(newMessage);
                }
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown() {
        if (!client.isClosed()) {
            //Prints information to the other clients about the disconnection.
            String disconnectInfo = screenName + " disconnected!";
            System.out.println(disconnectInfo);
            server.broadcast(new ServerMessage(disconnectInfo));
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
