/*
Author: Filip Hellgren

The Command superclass which describes the general blueprint of the other command classes.
 */

package commands;

public abstract class Command {
    protected static final String PREFIX = "/"; //The character(s) used to indicate the start of a command.
    protected String command;
    protected String description;
    protected int numArguments; //The number of arguments required by the command.

    public Command() {
    }

    abstract void executeCommand(CommandHandler commandHandler, String[] args);

    public String[] getCommandInfo() {
        //Returns a list containing the command's required prompt as well as a description of the command.
        String[] commandInfo = new String[2];
        commandInfo[1] = PREFIX + command;
        commandInfo[0] = description;
        return commandInfo;
    }
    public String getCommand() {
        return this.PREFIX + this.command;
    }
}
