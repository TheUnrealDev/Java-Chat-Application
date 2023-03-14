/*
Author: Filip Hellgren

The NamingHandler class which is used in order to change the name of the associated user and puts restrictions on the selected names.
 */

package mainPack;

import messages.ServerMessage;

import java.io.BufferedReader;
import java.io.IOException;

public class NamingHandler {
    Server server;
    ConnectionHandler client;
    BufferedReader in;
    String screenName;
    public NamingHandler(Server server, ConnectionHandler client) {
        this.server = server;
        this.client = client;
        this.in = client.getIn();
        this.screenName = client.getScreenName();
    }
    private boolean isNameInUse(String name) {
        // Returns a boolean containing whether the name is already used by another client connected to the server.
        String simplifiedName = simplifyName(name);

        for (ConnectionHandler connection : server.getConnections()) {
            String screenName = connection.getScreenName();
            if (screenName == null) { continue; }

            String otherName = simplifyName(screenName);
            if (simplifiedName.equals(otherName)) {
                return true;
            }
        }
        return false;
    }
    public static String simplifyName(String name) {
        // Simplifies the name (only for use in comparisons between names) in order for users not to be able to enter names that are very similar to others.
        // stops users from being able to use names that are the same when ignoring white spaces and case.
        //Ex. prevents User 1 being named "boris" at the same time as User 2 is named " Boris   "
        return name.trim().toLowerCase();
    }
    public boolean isNameValid(String name) {
        //Returns a boolean whether the name is valid (it is unique and not empty)
        if (name == null) { return false; }
        if (name.trim().isEmpty()) {
            client.sendMessage(new ServerMessage("Your name must not be empty!"));
            return false;
        }
        if (isNameInUse(name)) {
            client.sendMessage(new ServerMessage("This name is already in use!"));
            return false;
        }
        return true;
    }
    public String getValidName() {
        // Keeps requesting the user to input a name until a valid name has been given.
        String name;
        while (true) {
            client.sendMessage(new ServerMessage("Please input a screen name: "));
            try {
                String input = in.readLine();
                if (isNameValid(input)) {
                    name = input.trim();
                    break;
                }
            } catch (IOException e) {
                client.sendMessage(new ServerMessage("Your name was not changed, please try again."));
            }
        }

        return name;
    }
}
