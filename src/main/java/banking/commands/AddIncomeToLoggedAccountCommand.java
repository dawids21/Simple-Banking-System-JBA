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
        int amount;
        try {
            amount = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error while adding income");
            System.out.println();
            return;
        }
        var account = bank.getLoggedAccount();
        account.setBalance(account.getBalance() + amount);
        var success = bank.updateAccount(account);
        System.out.println(success ? "Income was added!" : "Error while adding income");
        System.out.println();
    }
}
