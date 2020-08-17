package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomCardGeneratorTest {

    private static final String IIN = "400000";
    private static final int ACCOUNT_ID = 0;

    private RandomCardGenerator randomCardGenerator;

    @BeforeEach
    void setUp() {
        randomCardGenerator = new RandomCardGenerator(IIN);
    }

    @Test
    void generated_card_is_not_null() {
        var card = randomCardGenerator.generate(ACCOUNT_ID);
        assertNotNull(card);
    }

    @Test
    void card_number_begins_with_given_iin() {
        var card = randomCardGenerator.generate(ACCOUNT_ID);
        assertTrue(card.getNumber()
                       .startsWith(RandomCardGeneratorTest.IIN));
    }

    @Test
    void card_number_contains_only_digits() {
        var card = randomCardGenerator.generate(ACCOUNT_ID);
        assertTrue(card.getNumber()
                       .matches("\\d+"));
    }

    @Test
    void card_number_has_16_chars() {
        var card = randomCardGenerator.generate(ACCOUNT_ID);
        assertEquals(16, card.getNumber()
                             .length());
    }

    @Test
    void two_generated_cards_should_be_unique() {
        var card1 = randomCardGenerator.generate(ACCOUNT_ID);
        var card2 = randomCardGenerator.generate(ACCOUNT_ID + 1);

        assertNotEquals(card1, card2);
    }

    @Test
    void card_pin_contains_only_digits() {
        var card = randomCardGenerator.generate(ACCOUNT_ID);
        assertTrue(card.getPin()
                       .matches("\\d+"));
    }

    @Test
    void card_pin_has_16_chars() {
        var card = randomCardGenerator.generate(ACCOUNT_ID);
        assertEquals(4, card.getPin()
                            .length());
    }
}