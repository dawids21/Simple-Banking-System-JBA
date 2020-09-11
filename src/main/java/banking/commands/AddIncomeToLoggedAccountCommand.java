package banking.commands;

import banking.Bank;

import java.util.Scanner;

public class AddIncomeToLoggedAccountCommand implements Command {

    private final Scanner input;
    private final Bank bank;

    public AddIncomeToLoggedAccountCommand(Scanner input, Bank bank) {
        this.input = input;
        this.bank = bank;
    }

    @Override
    public void execute() {
        System.out.println("Enter income:");
        var amount = input.nextInt();
        var account = bank.getLoggedAccount();
        account.setBalance(account.getBalance() + amount);
        var success = bank.updateAccount(account);
        System.out.println(success ? "Income was added!" : "Error while adding income");
    }
}
