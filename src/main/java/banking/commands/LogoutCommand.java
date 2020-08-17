package banking.commands;

import banking.Bank;

public class LogoutCommand implements Command {

    private final Bank bank;

    public LogoutCommand(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void execute() {
        System.out.println();
        if (bank.logout()) {
            System.out.println("You have successfully logged out!");
        } else {
            System.out.println("Error during logging out");
        }
        System.out.println();
    }
}
