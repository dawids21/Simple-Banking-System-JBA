package banking;

public class CreateAccountCommand implements Command {

    private final Bank bank;

    public CreateAccountCommand(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void execute() {
        var id = bank.createAccount();
        var card = bank.getCard(id);
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(card.getNumber());
        System.out.println("Your card PIN:");
        System.out.println(card.getPin());
        System.out.println();
    }
}
