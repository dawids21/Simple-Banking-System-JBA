package banking;

import banking.exceptions.BankException;

import java.sql.SQLException;

public class Bank {

    private final String iin;
    private final CardGenerator cardGenerator;
    private Account loggedAccount;
    private final AccountsDatabase accountsDatabase;

    public Bank(String iin, CardGenerator cardGenerator,
                AccountsDatabase accountsDatabase) {
        this.iin = iin;
        this.cardGenerator = cardGenerator;
        loggedAccount = null;
        this.accountsDatabase = accountsDatabase;
    }

    public Account createAccount() {
        return accountsDatabase.add(cardGenerator);
    }

    public Account getAccount(int accountId) throws BankException {
        var account = accountsDatabase.getById(accountId);
        if (account == null) {
            throw new BankException("Wrong account id");
        }
        return account;
    }

    public Card getCard(int accountId) throws BankException {
        return getAccount(accountId).getCard();
    }

    public boolean login(String cardNumber, String cardPin) {
        if (!cardNumber.startsWith(iin)) {
            return false;
        }

        var account = accountsDatabase.getByNumber(cardNumber);

        if (account != null && account.getCard()
                                      .getPin()
                                      .equals(cardPin)) {
            loggedAccount = account;
            return true;
        }

        return false;
    }

    public Account getLoggedAccount() {
        return loggedAccount;
    }

    public boolean isLogged() {
        return loggedAccount != null;
    }

    public boolean logout() {
        if (isLogged()) {
            loggedAccount = null;
            return true;
        }
        return false;
    }

    public boolean accountExists(Account account) {
        return accountsDatabase.exists(account);
    }

    public boolean updateAccount(Account account) {
        var success = false;
        if (accountExists(account)) {
            accountsDatabase.update(account);
            success = true;
        }
        return success;
    }

    public boolean closeAccount(Account account) throws BankException {
        if (!accountExists(account)) {
            throw new BankException("Account does not exists");
        }

        try {
            accountsDatabase.delete(account);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void transfer(Account originAccount, String destinationAccountNumber, int amount)
             throws BankException {
        if (originAccount.getCard()
                         .getNumber()
                         .equals(destinationAccountNumber)) {
            throw new BankException("You can't transfer money to the same account!");
        }
        if (!new LuhnChecksumChecker(destinationAccountNumber).correct()) {
            throw new BankException(
                     "Probably you made mistake in the card number. Please try again!");
        }
        if (!cardNumberExists(destinationAccountNumber)) {
            throw new BankException("Such card does not exist.");
        }
        if (originAccount.getBalance() < amount) {
            throw new BankException("Not enough money!");
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private boolean cardNumberExists(String destinationAccountNumber) {
        return accountsDatabase.existsByNumber(destinationAccountNumber);
    }
}
