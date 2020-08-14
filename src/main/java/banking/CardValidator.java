package banking;

import java.util.HashSet;
import java.util.Set;

public class CardValidator {

    private final Set<Card> cards;

    public CardValidator(Set<Card> cards) {
        this.cards = cards;
    }

    public boolean validate(String cardNumber, String cardPin) {
        var card = new Card(cardNumber, cardPin);

        return cards.contains(card);
    }
}
