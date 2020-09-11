package banking;

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

    public Account getAccount(int accountId) {
        var account = accountsDatabase.getById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Wrong account id");
        }
        return account;
    }

    public Card getCard(int accountId) {
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
}
