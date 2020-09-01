package banking.commands;

import banking.Bank;

public class CreateAccountCommand implements Command {

    private final Bank bank;

    public CreateAccountCommand(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void execute() {
        var card = bank.createAccount().getCard();
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(card.getNumber());
        System.out.println("Your card PIN:");
        System.out.println(card.getPin());
        System.out.println();
    }
}
