package banking.commands;

import banking.Account;
import banking.Bank;
import banking.exceptions.BankException;

public class CreateAccountCommand implements Command {

    private final Bank bank;

    public CreateAccountCommand(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void execute() {
        Account account;
        try {
            account = bank.getAccount(bank.createAccount());
        } catch (BankException e) {
            System.out.println(e.getMessage());
            return;
        }

        var card = account.getCard();
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(card.getNumber());
        System.out.println("Your card PIN:");
        System.out.println(card.getPin());
        System.out.println();
    }
}
