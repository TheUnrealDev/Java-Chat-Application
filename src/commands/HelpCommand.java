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
        ConnectionHandler client = commandHandler.getClient();

        ArrayList<String> allCommandsInformation = new ArrayList<>();

        for (Command command: commandHandler.getCommands()) {
            String[] commandInfo = command.getCommandInfo();
            Collections.addAll(allCommandsInformation, commandInfo);
        }

        for(String str: allCommandsInformation) {
            System.out.println(str);
            client.sendMessage(new ServerMessage(str));
        }
    }
}
