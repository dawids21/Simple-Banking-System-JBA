package banking.commands;

import banking.Bank;

import java.util.Scanner;

public class LoginCommand implements Command {

    private final Scanner input;
    private final Bank bank;

    public LoginCommand(Scanner input, Bank bank) {
        this.input = input;
        this.bank = bank;
    }


    @Override
    public void execute() {
        System.out.println("Enter your card number:");
        var cardNumber = input.nextLine();
        System.out.println("Enter your PIN");
        var cardPin = input.nextLine();
        System.out.println();
        if (cardNumber.length() != 16 && cardPin.length() != 4) {
            System.out.println("Card number should have 16 digits and pin 4 digits");
            return;
        }
        if (bank.login(cardNumber, cardPin)) {
            System.out.println("You have successfully logged in!");
        } else {
            System.out.println("Wrong card number or PIN!");
        }
        System.out.println();
    }
}
