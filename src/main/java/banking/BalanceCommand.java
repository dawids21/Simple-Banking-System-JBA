package banking;

public class BalanceCommand implements Command {

    private final Account account;

    public BalanceCommand(Account account) {
        this.account = account;
    }

    @Override
    public void execute() {
        System.out.println();
        if (account != null) {
            System.out.println("Balance: " + account.getBalance());
        } else {
            System.out.println("Balance: unknown");
        }
        System.out.println();
    }
}
