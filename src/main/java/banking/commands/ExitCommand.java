package banking.commands;

import java.util.Scanner;

public class ExitCommand implements Command {

    private final Scanner input;

    public ExitCommand(Scanner input) {
        this.input = input;
    }

    @Override
    public void execute() {
        input.close();
        System.out.println("Bye!");
    }
}
