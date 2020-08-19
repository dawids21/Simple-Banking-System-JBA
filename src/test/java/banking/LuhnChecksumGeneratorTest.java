package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LuhnChecksumGeneratorTest {

    @Test
    void throws_exception_if_card_number_has_not_only_digits() {

        assertThrows(IllegalArgumentException.class,
                     () -> new LuhnChecksumGenerator("21wqe21987d289f78");
    }

    @Test
    void returns_checksum() {

        var luhnChecksumGenerator = new LuhnChecksumGenerator("400000844943340");

        assertEquals(3, luhnChecksumGenerator.generate());
    }
}