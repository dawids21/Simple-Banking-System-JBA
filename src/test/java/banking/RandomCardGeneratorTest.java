package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomCardGeneratorTest {

    private static final String IIN = "400000";
    private static final int ACCOUNT_ID = 0;
    Card card;

    private RandomCardGenerator randomCardGenerator;

    @BeforeEach
    void setUp() {
        card = new RandomCardGenerator(IIN).generate(IIN, ACCOUNT_ID);
    }

    @Test
    void generated_card_is_not_null() {
        assertNotNull(card);
    }

    @Test
    void card_number_begins_with_given_iin() {
        assertTrue(card.getNumber()
                       .startsWith(RandomCardGeneratorTest.IIN));
    }

    @Test
    void card_number_contains_only_digits() {
        assertTrue(card.getNumber()
                       .matches("\\d+"));
    }

    @Test
    void card_number_has_16_chars() {
        assertEquals(16, card.getNumber()
                             .length());
    }

    @Test
    void two_generated_cards_should_be_unique() {
        var anotherCard = new RandomCardGenerator(IIN).generate(IIN, ACCOUNT_ID + 1);

        assertNotEquals(card, anotherCard);
    }

    @Test
    void card_pin_contains_only_digits() {
        assertTrue(card.getPin()
                       .matches("\\d+"));
    }

    @Test
    void card_pin_has_16_chars() {
        assertEquals(4, card.getPin()
                            .length());
    }

    @Test
    void generated_card_has_correct_checksum() {
        var sum = 0;
        for (int i = 0; i < card.getNumber()
                                .length(); i++) {
            int digit = card.getNumber()
                            .charAt(i) - '0';
            if (i % 2 == 0) {
                digit *= 2;
            }
            if (digit > 10) {
                digit -= 9;
            }
            sum += digit;
        }

        assertEquals(0, sum % 10);
    }
}