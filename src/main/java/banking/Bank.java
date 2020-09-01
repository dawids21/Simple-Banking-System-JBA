package banking;

import java.sql.Connection;
import java.util.ArrayList;

public class Bank {

    private final String iin;
    private final CardGenerator cardGenerator;
    private final ArrayList<Account> accounts;
    private Account loggedAccount;
    private final Connection dbConnection;

    public Bank(String iin, CardGenerator cardGenerator, Connection dbConnection) {
        this.iin = iin;
        this.cardGenerator = cardGenerator;
        accounts = new ArrayList<>();
        loggedAccount = null;
        this.dbConnection = dbConnection;
    }

    public int createAccount() {
        int accountId = accounts.size();
        accounts.add(new Account(accountId, cardGenerator.generate(iin, accountId)));
        return accountId;
    }

    public Account getAccount(int accountId) {
        if (accounts.size() <= accountId) {
            return null;
        }
        return accounts.get(accountId);
    }

    public Card getCard(int accountId) {
        return accounts.get(accountId)
                       .getCard();
    }

    public boolean login(String cardNumber, String cardPin) {
        var accountId =
                 Integer.parseInt(cardNumber.substring(6, cardNumber.length() - 1));
        if (!cardNumber.startsWith(iin)) {
            return false;
        }
        if (getAccount(accountId) == null) {
            return false;
        }

        if (validateCard(accountId, cardPin)) {
            loggedAccount = accounts.get(accountId);
            return true;
        }

        return false;
    }

    private boolean validateCard(int accountId, String cardPin) {
        return accounts.get(accountId)
                       .getCard()
                       .getPin()
                       .equals(cardPin);
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
}
