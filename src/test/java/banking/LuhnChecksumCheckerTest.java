package banking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LuhnChecksumCheckerTest {

    @Test
    @DisplayName("returns true when number passes luhn validation")
    void returns_true_when_number_passes_luhn_validation() {
        assertTrue(new LuhnChecksumChecker("4000008449433403").correct());
    }

    @Test
    @DisplayName("returns false when number does not pass luhn validation")
    void returns_false_when_number_does_not_pass_luhn_validation() {
        assertFalse(new LuhnChecksumChecker("4000008449433408").correct());
    }

    @Test
    @DisplayName("returns false when digits are swapped")
    void returns_false_when_digits_are_swapped() {
        assertFalse(new LuhnChecksumChecker("4000008449343403").correct());
    }

    @Test
    @DisplayName("returns false when one digit is mistaken")
    void returns_false_when_one_digit_is_mistaken() {
        assertFalse(new LuhnChecksumChecker("4000009449343403").correct());
    }

}