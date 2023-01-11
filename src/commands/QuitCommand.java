package commands;

import mainPack.ConnectionHandler;

public class QuitCommand extends Command {
    public QuitCommand() {
        command = "quit";
        description = "Exits the chat room and disconnects.";
        numArguments = 0;
    }

    @Override
    public void executeCommand(CommandHandler commandHandler, String[] arguments) {
        ConnectionHandler client = commandHandler.getClient();
        client.shutdown();
    }
}
