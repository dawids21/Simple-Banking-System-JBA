package banking;

public class Account {

    private final Card card;
    private long balance;

    public Account(Card card) {
        this.card = card;
        this.balance = 0;
    }

    public Account(Card card, long balance) {
        this.card = card;
        this.balance = balance;
    }

    public Card getCard() {
        return card;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
