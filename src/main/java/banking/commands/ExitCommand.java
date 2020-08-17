package banking.commands;

import java.util.Scanner;

public class ExitCommand implements Command {

    private final Scanner input;

    public ExitCommand(Scanner input) {
        this.input = input;
    }

    @Override
    public void execute() {
        System.out.println();
        input.close();
        System.out.println("Bye!");
    }
}
