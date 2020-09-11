package banking.commands;

import banking.Account;

public class BalanceCommand implements Command {

    private final Account account;

    public BalanceCommand(Account account) {
        this.account = account;
    }

    @Override
    public void execute() {
        if (account != null) {
            System.out.println("Balance: " + account.getBalance());
        } else {
            System.out.println("Balance: unknown");
        }
        System.out.println();
    }
}
