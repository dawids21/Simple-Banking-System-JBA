package banking;

import java.util.ArrayList;
import java.util.HashSet;

public class Bank {

    private final CardGenerator cardGenerator;
    private final ArrayList<Account> accounts;
    private final HashSet<Card> cards;
    private Account loggedAccount;

    public Bank(CardGenerator cardGenerator) {
        this.cardGenerator = cardGenerator;
        accounts = new ArrayList<>();
        cards = new HashSet<>();
        loggedAccount = null;
    }

    public int createAccount() {
        //TODO implement createAccount
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Account getAccount(int accountID) {
        //TODO implement getAccount
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Card getCard(int accountID) {
        //TODO implement getCard
        throw new UnsupportedOperationException("Not implemented yet");
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
