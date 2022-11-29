package Commands;

import mainPack.ConnectionHandler;
import mainPack.NamingHandler;
import mainPack.Server;

public class NicknameCommand extends Command {
    public NicknameCommand() {
        command = "nick";
        description = "Changes your screen name. \nArguments: (New Name)";
        numArguments = 1;
    }

    @Override
    public void executeCommand(CommandHandler commandHandler, String[] args) {
        Server server = commandHandler.getServer();
        ConnectionHandler client = commandHandler.getClient();
        NamingHandler namingHandler = commandHandler.getNamingHandler();

        String newName = args[1].trim();

        if (namingHandler.isNameValid(newName)) {
            String nameChangeInfo = "'" + client.getScreenName() + "' just changed their name to '" + newName + "'!";

            server.log(nameChangeInfo);
            server.broadcast(nameChangeInfo);

            client.setScreenName(newName);
            client.sendMessage("Your name was successfully changed to '" + newName + "'");
        }
    }
}
