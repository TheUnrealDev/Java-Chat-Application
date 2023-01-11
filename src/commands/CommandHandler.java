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
        namingHandler = new NamingHandler(server, client);

        commands = new ArrayList<>();
        commands.add(new QuitCommand());
        commands.add(new HelpCommand());
        commands.add(new NicknameCommand());
    }

    public String[] GetArguments(String message, int numArguments) {
        String[] arguments = message.trim().split(" ", numArguments + 1);
        return arguments;
    }

    public boolean ProcessCommands(String message) {
        /*
          Checks for commands in the text written by the client and processes the commands.
          Returns boolean "isCommandProcessed" which is set to false only if no command is recognized.
         */
        boolean isCommandProcessed = false;
        try {
            String lowerCaseMessage = message.toLowerCase();
            for (Command command : commands) {
                String requiredCommand = Command.PREFIX + command.getCommand();
                if (!lowerCaseMessage.startsWith(requiredCommand)) { continue; }

                String[] arguments = GetArguments(message, command.numArguments);
                if (arguments.length - 1 < command.numArguments) {
                    String errorInformation = "You provided " + (arguments.length - 1) + " arguments. " +
                            "The specified command requires " + command.numArguments + " arguments. "
                            + "Please try again.";
                    client.sendMessage(new ServerMessage(errorInformation));
                    break;
                }

                command.executeCommand(this, arguments);
                isCommandProcessed = true;
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
