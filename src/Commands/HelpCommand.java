package Commands;

import mainPack.ConnectionHandler;

public class HelpCommand extends Command {
    public HelpCommand() {
        command = "help";
        description = "Displays information about the available commands.";
        numArguments = 0;
    }

    public void executeCommand(CommandHandler commandHandler, String[] args) {
        ConnectionHandler client = commandHandler.getClient();

        client.sendMessage("""
                ----------------------------------
                Commands
                """);

        for (Command command: commandHandler.getCommands()) {
            client.sendMessage(command.getCommandInfo());
        }
    }
}
