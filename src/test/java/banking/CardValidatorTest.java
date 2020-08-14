package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CardValidatorTest {

    private static final String CARD_IIN = "123456";
    private static final String CARD_ACCOUNT_ID = "123456789";
    private static final String CARD_CHECKSUM = "0";

    private static final String CARD_PIN = "1234";
    private CardValidator validator;
    private HashSet<Card> cards;

    @BeforeEach
    void setUp() {
        cards = new HashSet<>();
        cards.add(new Card(CARD_IIN, CARD_ACCOUNT_ID, CARD_CHECKSUM, CARD_PIN));
        validator = new CardValidator(cards);
    }

    @Test
    void returns_true_when_number_and_pin_are_matched() {
        assertTrue(validator.valid(CARD_IIN + CARD_ACCOUNT_ID + CARD_CHECKSUM, CARD_PIN));
    }

    @Test
    void returns_false_when_number_does_not_exist() {
        assertTrue(validator.valid("1234567890123456", ""));
    }

    @Test
    void returns_false_when_number_and_pin_are_not_matched() {
        assertFalse(validator.valid(CARD_IIN + CARD_ACCOUNT_ID + CARD_CHECKSUM, "0000"));
    }
}