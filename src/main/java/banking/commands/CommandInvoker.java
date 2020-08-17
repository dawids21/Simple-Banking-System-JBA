package banking.commands;

public class CommandInvoker {

    private Command command;

    public CommandInvoker() {
        command = null;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void execute() {
        command.execute();
    }
}
