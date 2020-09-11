package banking.commands;

import banking.Bank;

public class AddIncomeToLoggedAccountCommand implements Command {

    private final int amount;
    private final Bank bank;

    public AddIncomeToLoggedAccountCommand(int amount, Bank bank) {
        this.amount = amount;
        this.bank = bank;
    }

    @Override
    public void execute() {
        var account = bank.getLoggedAccount();
        account.setBalance(account.getBalance() + amount);
        bank.updateAccount(account);
    }
}
