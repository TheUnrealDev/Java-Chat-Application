package Commands;

public abstract class Command {
    protected static final String PREFIX = "/";
    protected String command;
    protected String description;
    protected int numArguments;

    public Command() {
    }

    abstract void executeCommand(CommandHandler commandHandler, String[] args);

    public String getCommandInfo() {
        String helpInformation = PREFIX + command + "\n" + description + "\n";
        return helpInformation;
    }
    public String getCommand() {
        return this.command;
    }
}
