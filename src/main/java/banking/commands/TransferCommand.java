package banking.commands;

import banking.Bank;
import banking.exceptions.BankException;

import java.util.Scanner;

public class TransferCommand implements Command {

    private final Scanner input;
    private final Bank bank;

    public TransferCommand(Scanner input, Bank bank) {
        this.input = input;
        this.bank = bank;
    }

    @Override
    public void execute() {
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        var destination = input.nextLine();
        try {
            bank.checkCardNumbersForTransfer(bank.getLoggedAccount()
                                                 .getCard()
                                                 .getNumber(), destination);
        } catch (BankException e) {
            System.out.println(e.getMessage());
            System.out.println();
            return;
        }
        System.out.println("Enter how much money you want to transfer:");
        int amount;
        try {
            amount = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("You have to input number");
            System.out.println();
            return;
        }
        try {
            bank.transfer(bank.getLoggedAccount()
                              .getId(), destination, amount);
        } catch (BankException e) {
            System.out.println(e.getMessage());
            System.out.println();
            return;
        }
        System.out.println("Success!");
        System.out.println();
    }
}
