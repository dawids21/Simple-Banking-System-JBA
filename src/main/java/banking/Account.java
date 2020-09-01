package banking;

public class Account {

    private final int id;
    private final Card card;
    private long balance;

    public Account(int id, Card card) {
        this.id = id;
        this.card = card;
        this.balance = 0;
    }

    public Account(int id, Card card, long balance) {
        this.id = id;
        this.card = card;
        this.balance = balance;
    }

    public int getId() {
        return id;
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
