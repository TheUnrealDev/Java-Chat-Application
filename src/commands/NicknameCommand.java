/*
Author: Filip Hellgren

The Nickname Command which allows the changing of the client's screen name.
Subclass of Command
 */

package commands;

import mainPack.ConnectionHandler;
import mainPack.NamingHandler;
import mainPack.Server;
import messages.ServerMessage;

public class NicknameCommand extends Command {
    public NicknameCommand() {
        command = "nick";
        description = "Changes your screen name. Arguments: (New Name)";
        numArguments = 1;
    }

    @Override
    public void executeCommand(CommandHandler commandHandler, String[] args) {
        Server server = commandHandler.getServer();
        ConnectionHandler client = commandHandler.getClient();
        NamingHandler namingHandler = commandHandler.getNamingHandler(); //Gets a reference to the object which controls renaming of the client.

        String newName = args[1].trim(); //Gets the first argument passed with the command. (The desired new name)

        if (namingHandler.isNameValid(newName)) {
            //Verifies that the name is a valid string that is not empty and not currently in use by another client.
            //If valid then change the name of the client and send the messages about the change to the server log and to the clients.
            String nameChangeInfo = "'" + client.getScreenName() + "' just changed their name to '" + newName + "'!";

            server.log(nameChangeInfo);
            server.broadcast(new ServerMessage(nameChangeInfo));

            client.setScreenName(newName);
            client.sendMessage(new ServerMessage("Your name was successfully changed to '" + newName + "'"));
        }
    }
}
