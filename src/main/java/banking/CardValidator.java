package banking;

import java.util.HashSet;

public class CardValidator {

    private final HashSet<Card> cards;

    public CardValidator(HashSet<Card> cards) {
        this.cards = cards;
    }

    public boolean validate(String cardNumber, String cardPin) {
        var card = new Card(cardNumber, cardPin);

        return cards.contains(card);
    }
}
