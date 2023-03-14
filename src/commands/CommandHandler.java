/*
Author: Filip Hellgren

The CommandHandler class responsible for initializing the commands and processing the commands made by the player.
 */

package commands;

import mainPack.NamingHandler;
import mainPack.Server;
import mainPack.ConnectionHandler;
import messages.ServerMessage;

import java.util.ArrayList;

public class CommandHandler {
    protected final ArrayList<Command> commands;
    private final Server server;
    private final ConnectionHandler client;
    private final NamingHandler namingHandler;

    public CommandHandler(Server server, ConnectionHandler client) {
        this.server = server;
        this.client = client;
        namingHandler = new NamingHandler(server, client); //Creates an instance of the NamingHandler which handles the changing of a client's screen name.

        commands = new ArrayList<>();
        commands.add(new HelpCommand());
        commands.add(new NicknameCommand());
    }

    public String[] GetArguments(String message, int numArguments) {
        /*
        Splits the command String typed by the players at every space in order to isolate the arguments,
        then returns an array containing the arguments.
        The number of arguments saved depends on how many arguments the command requires.
        */
        String[] arguments = message.trim().split(" ", numArguments + 1);
        return arguments;
    }

    public boolean ProcessCommands(String message) {
        /*
        Checks for commands in the text written by the client and processes the commands.
        Returns boolean "isCommandProcessed" which is set to false only if no command is recognized.
        */
        if (!message.startsWith(Command.PREFIX)) {
            //If the message does not start with the command prefix it is not a command and nothing should be processed.
            return false;
        }

        boolean isCommandProcessed = false;
        try {
            String lowerCaseMessage = message.toLowerCase();
            for (Command command : commands) {
                String requiredCommand = command.getCommand(); //The exact String required to execute the command Ex. /help
                if (!lowerCaseMessage.startsWith(requiredCommand)) { continue; } //If the string does not start with the required command then for check the next command.

                isCommandProcessed = true;

                String[] arguments = GetArguments(message, command.numArguments); //Gets the arguments provided to the command from the input string.
                if (arguments.length - 1 < command.numArguments) {
                    //Not enough arguments were provided and the command could not be executed.

                    String errorInformation = "You provided " + (arguments.length - 1) + " arguments. " +
                            "The specified command requires " + command.numArguments + " arguments. "
                            + "Use '/help' for more information.";
                    client.sendMessage(new ServerMessage(errorInformation));
                    break;
                }

                command.executeCommand(this, arguments);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            client.sendMessage(new ServerMessage("There was an error with your command, please try again."));
        }
        return isCommandProcessed;
    }

    public Server getServer() {
        return server;
    }

    public ConnectionHandler getClient() {
        return client;
    }

    public NamingHandler getNamingHandler() {
        return namingHandler;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}
