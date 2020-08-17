package banking;

import java.util.Objects;

public class Card {

    private final String iin;
    private final String accountId;
    private final String checksum;
    private final String pin;

    public Card(String number, String pin) {
        this(number.substring(0, 6), number.substring(6, number.length() - 1),
             number.substring(number.length() - 1), pin);
    }

    public Card(String iin, String accountId, String checksum, String pin) {
        this.iin = iin;
        this.accountId = accountId;
        this.checksum = checksum;
        this.pin = pin;
    }

    public String getNumber() {
        return iin + accountId + checksum;
    }

    public String getAccountID() {
        return accountId;
    }

    public String getPin() {
        return pin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return Objects.equals(iin, card.iin) &&
               Objects.equals(accountId, card.accountId) &&
               Objects.equals(checksum, card.checksum) &&
               Objects.equals(getPin(), card.getPin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(iin, accountId, checksum, getPin());
    }

    @Override
    public String toString() {
        return "Card{" + "number='" + getNumber() + '\'' + ", pin='" + pin + '\'' + '}';
    }
}
