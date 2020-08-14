package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardGeneratorTest {

    private static final String IIN = "400000";

    private CardGenerator cardGenerator;

    @BeforeEach
    void setUp() {
        cardGenerator = new CardGenerator(IIN);
    }

    @Test
    void generated_card_is_not_null() {
        var card = cardGenerator.generate();
        assertNotNull(card);
    }

    @Test
    void card_number_begins_with_given_iin() {
        var card = cardGenerator.generate();
        assertTrue(card.getNumber()
                       .startsWith(CardGeneratorTest.IIN));
    }

    @Test
    void card_number_contains_only_digits() {
        var card = cardGenerator.generate();
        assertTrue(card.getNumber()
                       .matches("\\d+"));
    }

    @Test
    void card_number_has_16_chars() {
        var card = cardGenerator.generate();
        assertEquals(16, card.getNumber()
                             .length());
    }

    @Test
    void two_generated_cards_should_be_unique() {
        var card1 = cardGenerator.generate();
        var card2 = cardGenerator.generate();

        assertNotEquals(card1, card2);
    }

    @Test
    void card_pin_contains_only_digits() {
        var card = cardGenerator.generate();
        assertTrue(card.getPin()
                       .matches("\\d+"));
    }

    @Test
    void card_pin_has_16_chars() {
        var card = cardGenerator.generate();
        assertEquals(4, card.getPin()
                            .length());
    }
}