package commands;

public abstract class Command {
    protected static final String PREFIX = "/";
    protected String command;
    protected String description;
    protected int numArguments;

    public Command() {
    }

    abstract void executeCommand(CommandHandler commandHandler, String[] args);

    public String[] getCommandInfo() {
        String[] commandInfo = new String[2];
        commandInfo[1] = PREFIX + command;
        commandInfo[0] = description;
        return commandInfo;
    }
    public String getCommand() {
        return this.command;
    }
}
