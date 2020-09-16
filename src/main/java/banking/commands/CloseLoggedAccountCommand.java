package banking.commands;

import banking.Bank;
import banking.exceptions.BankException;

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
        } catch (BankException e) {
            e.printStackTrace();
            success = false;
        }
        bank.logout();
        System.out.println(success ? "The account has been closed!" :
                                    "Error while closing account");
        System.out.println();
    }
}
