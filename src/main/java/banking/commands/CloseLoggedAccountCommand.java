package banking.commands;

import banking.Bank;
import banking.exceptions.AccountNotFoundException;

public class CloseLoggedAccountCommand implements Command {

    private final Bank bank;

    public CloseLoggedAccountCommand(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void execute() {
        var success = true;
        try {
            success = bank.closeAccount(bank.getLoggedAccount());
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
            success = false;
        }
        bank.logout();
        System.out.println(success ? "The account has been closed!" : "Error while closing account");
    }
}
