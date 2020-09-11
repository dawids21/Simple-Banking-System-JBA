package banking;

import org.junit.jupiter.api.*;

class AccountsDatabaseTest {

    static class TestCardGenerator implements CardGenerator {

        private final String iin;
        private final String cardPin;

        public TestCardGenerator(String iin, String cardPin) {
            this.iin = iin;
            this.cardPin = cardPin;
        }

        @Override
        public Card generate(int accountId) {
            return new Card(iin + String.format("%09d", accountId) + "0", cardPin);
        }
    }

    private AccountsDatabase database;

    @BeforeEach
    void setUp() {
        database = new AccountsDatabase("jdbc:sqlite:test.db");
    }

    @AfterEach
    void tearDown() {
        database.clear();
    }

    @Nested
    class exists {

        private static final String IIN = "123456";
        private static final String CARD_PIN = "1234";

        @Test
        void returns_true_when_account_is_in_database() {
            var createdAccount = database.add(new TestCardGenerator(IIN, CARD_PIN));

            Assertions.assertTrue(database.exists(createdAccount));
        }
    }
}