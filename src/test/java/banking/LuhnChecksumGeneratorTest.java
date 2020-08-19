package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LuhnChecksumGeneratorTest {

    @Test
    void throws_exception_if_card_number_len_is_less_than_15() {

        assertThrows(IllegalArgumentException.class,
                     () -> new LuhnChecksumGenerator("12345678901234"));
    }

    @Test
    void throws_exception_if_card_number_len_is_more_than_15() {

        assertThrows(IllegalArgumentException.class,
                     () -> new LuhnChecksumGenerator("1234567890123456"));
    }

    @Test
    void returns_checksum() {

        var luhnChecksumGenerator = new LuhnChecksumGenerator("400000844943340");

        assertEquals(3, luhnChecksumGenerator.generate());
    }
}