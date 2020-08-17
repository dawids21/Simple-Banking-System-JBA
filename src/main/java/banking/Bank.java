package banking;

import java.util.ArrayList;

public class Bank {

    private final CardGenerator cardGenerator;
    private final ArrayList<Account> accounts;
    private Account loggedAccount;

    public Bank(CardGenerator cardGenerator) {
        this.cardGenerator = cardGenerator;
        accounts = new ArrayList<>();
        loggedAccount = null;
    }

    public int createAccount() {
        int accountId = accounts.size();
        accounts.add(new Account(cardGenerator.generate(accountId)));
        return accountId;
    }

    public Account getAccount(int accountId) {
        return accounts.get(accountId);
    }

    public Card getCard(int accountId) {
        return accounts.get(accountId)
                       .getCard();
    }

    private boolean validateCard(String cardNumber, String cardPin) {
        //TODO implement validateCard
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean login(String cardNumber, String cardPin) {
        //TODO implement login
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Account getLoggedAccount() {
        //TODO implement getLoggedAccount
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isLogged() {
        //TODO implement isLogged
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean logout() {
        //TODO implement logout
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
