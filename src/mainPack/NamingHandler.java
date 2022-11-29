package mainPack;

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
        String simplifiedName = simplifyName(name);

        for (ConnectionHandler connection : server.getConnections()) {
            String screenName = connection.getScreenName();
            if (screenName == null) { continue; }

            String otherName = simplifyName(screenName);
            System.out.println(otherName);
            System.out.println(simplifiedName);
            System.out.println(otherName.equals(simplifiedName));
            if (simplifiedName.equals(otherName)) {
                return true;
            }
        }
        return false;
    }
    public static String simplifyName(String name) {
        return name.trim().toLowerCase();
    }
    public boolean isNameValid(String name) {
        if (name == null) { return false; }
        if (name.trim().isEmpty()) {
            client.sendMessage("Your name must not be empty!");
            return false;
        }
        if (isNameInUse(name)) {
            client.sendMessage("This name is already in use!");
            return false;
        }
        return true;
    }
    public String getValidName() {
        String name = "";
        while (true) {
            client.sendMessage("Please input a screen name: ");
            try {
                String input = in.readLine();
                if (isNameValid(input)) {
                    name = input.trim();
                    break;
                }
            } catch (IOException e) {
                client.sendMessage("Your name was not changed, please try again.");
            }
        }

        return name;
    }
}
