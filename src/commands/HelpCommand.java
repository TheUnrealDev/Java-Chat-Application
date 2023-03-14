/*
Author: Filip Hellgren

The Help Command which displays information about available commands.
Subclass of Command
 */

package commands;

import mainPack.ConnectionHandler;
import messages.ServerMessage;

import java.util.ArrayList;
import java.util.Collections;

public class HelpCommand extends Command {
    public HelpCommand() {
        command = "help";
        description = "Displays information about the available commands.";
        numArguments = 0;
    }

    @Override
    public void executeCommand(CommandHandler commandHandler, String[] args) {
        //Loops through available commands and prints information about them to the client.
        ConnectionHandler client = commandHandler.getClient();

        ArrayList<String> allCommandsInformation = new ArrayList<>();

        for (Command command: commandHandler.getCommands()) {
            //Loops through the commands and adds both the required command Ex. '/help' and it's description to an ArrayList, as separate elements.
            //They are required to be separate elements in order to be properly displayed in the UI as newline characters are not able to be sent through the client socket.
            String[] commandInfo = command.getCommandInfo();
            Collections.addAll(allCommandsInformation, commandInfo);
        }

        for(String str: allCommandsInformation) {
            //Loop through all information about the commands and send each piece of information as a message to the client.
            client.sendMessage(new ServerMessage(str));
        }
    }
}
