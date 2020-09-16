package banking.commands;

import banking.Account;
import banking.Bank;
import banking.exceptions.BankException;

public class BalanceCommand implements Command {

    private final Bank bank;

    public BalanceCommand(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void execute() {
        Account account;
        try {
            account = bank.getLoggedAccount();
        } catch (BankException e) {
            System.out.println(e.getMessage());
            System.out.println();
            return;
        }
        System.out.println("Balance: " + account.getBalance());
        System.out.println();
    }
}
