package banking;

import java.util.Objects;

public class Card {

    private final String iin;
    private final String accountID;
    private final String checksum;
    private final String pin;

    public Card(String iin, String accountID, String checksum, String pin) {
        this.iin = iin;
        this.accountID = accountID;
        this.checksum = checksum;
        this.pin = pin;
    }

    public String getNumber() {
        return iin + accountID + checksum;
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
               Objects.equals(accountID, card.accountID) &&
               Objects.equals(checksum, card.checksum) &&
               Objects.equals(getPin(), card.getPin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(iin, accountID, checksum, getPin());
    }

    @Override
    public String toString() {
        return "Card{" + "number='" + getNumber() + '\'' + ", pin='" + pin + '\'' + '}';
    }
}
