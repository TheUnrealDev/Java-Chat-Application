/*
Author: Filip Hellgren

The runnable Server class responsible for initializing a ServerSocket on a port and listen to requests and handle Client connections.
 */

package mainPack;

import messages.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Server implements Runnable {
    private final ArrayList<ConnectionHandler> connections;
    private final ServerLog serverLog;
    private ServerSocket server;
    private boolean isClosed;
    private ExecutorService threadPool;
    public Server() {
        //Initializes the server variables and creates a connection to a server log.
        connections = new ArrayList<>();
        isClosed = false;
        serverLog = new ServerLog();
        log("The server has started.");
    }
    @Override
    public void run() {
        //Attempts to start the server and monitor for connections.
        try {
            server = new ServerSocket(9999); //Creates a new Server Socket instance to monitor and send data through the specified port and listen to requests.
            threadPool = Executors.newCachedThreadPool(); //Creates a cached thread pool which will dynamically create and handle threads used by the ConnectionHandlers.
            while (!isClosed) {
                Socket client = server.accept(); //Accepts ingoing connection attempts to the ServerSocket and creates a Socket instance to reference this particular connection.
                ConnectionHandler clientConnection = new ConnectionHandler(this, client); //Creates an instance of the ConnectionHandler class which purpose is to handle the information transferred for this connection.
                connections.add(clientConnection);
                threadPool.execute(clientConnection); //Assigns a thread from the thread pool to run this ConnectionHandler instance and starts executing it.
            }
        } catch (Exception e) {
            log("An error occurred, shutting down the server.");
            log(e.toString());
            shutdownServer();
        }
    }
    public void broadcast(Message message) {
        //Broadcasts a message to be displayed for all clients.
        for (ConnectionHandler connection : connections) {
            //Loop through connections and send the message to each valid connection.
            if (connection == null) { continue; }
            if (connection.getScreenName() == null) { continue; }

            connection.sendMessage(message);
        }
    }

    public void log(String message) {
        //Writes the provided message to the server log file along with the current time at the moment of the log.
        serverLog.WriteToFile(message);
    }
    public void shutdownServer() {
        //Closes all active connections and closes the ServerSocket.
        try {
            isClosed = true;
            if (!server.isClosed()) {
                server.close();
            }
            for (ConnectionHandler connection : connections) {
                connection.shutdown();
            }
        } catch (IOException e) {
            log(e.toString());
        }
    }

    public void removeClient(ConnectionHandler targetClient) {
        //Removes the target client from the list of active connections.
        connections.remove(targetClient);
    }
    public ArrayList<ConnectionHandler> getConnections() {
        return this.connections;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}
