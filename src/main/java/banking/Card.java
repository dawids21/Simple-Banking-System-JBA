package banking;

import java.util.Objects;

public class Card {

    private final String number;
    private final String pin;

    public Card(String number, String pin) {
        this.number = number;
        this.pin = pin;
    }

    public String getNumber() {
        return number;
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
        return Objects.equals(getNumber(), card.getNumber()) &&
               Objects.equals(getPin(), card.getPin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getPin());
    }

    @Override
    public String toString() {
        return "Card{" + "number='" + number + '\'' + ", pin='" + pin + '\'' + '}';
    }
}
