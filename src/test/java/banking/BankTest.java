package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    static class TestCardGenerator implements CardGenerator {

        private final String cardNumber;
        private final String cardPin;

        public TestCardGenerator(String cardNumber, String cardPin) {
            this.cardNumber = cardNumber;
            this.cardPin = cardPin;
        }

        @Override
        public Card generate() {
            return new Card(cardNumber, cardPin);
        }
    }

    static class create_account {

        private static final String NUMBER = "1234567890123456";
        private static final String PIN = "1234";
        Bank bank;
        String accountID;

        @BeforeEach
        void setUp() {
            bank = new Bank(new BankTest.TestCardGenerator(NUMBER, PIN));
            accountID = bank.createAccount();
        }

        @Test
        void creates_new_account() {
            assertThrows(IllegalArgumentException.class,
                         () -> bank.getAccount(accountID));
        }

        @Test
        void new_account_has_balance_zero() {
            assertEquals(0, bank.getAccount(accountID)
                                .getBalance());
        }

        @Test
        void new_card_is_generated_when_creating_new_account() {
            assertNotNull(bank.getCard(accountID));
        }
    }

    static class login {

        private static final String NUMBER = "1234567890123456";
        private static final String PIN = "1234";
        Bank bank;

        @BeforeEach
        void setUp() {
            bank = new Bank(new BankTest.TestCardGenerator(NUMBER, PIN));
            bank.createAccount();
        }

        @Test
        void returns_true_when_successfully_logged_in() {
            assertTrue(bank.login(NUMBER, PIN));
            assertTrue(bank.isLogged());
        }

        @Test
        void returns_false_when_account_does_not_exist() {
            assertFalse(bank.login("0000000000000000", "1234"));
            assertFalse(bank.isLogged());
        }

        @Test
        void returns_false_when_pin_is_not_correct() {
            assertFalse(bank.login(NUMBER, "0000"));
            assertFalse(bank.isLogged());
        }
    }

    static class get_logged_account {

        private static final String NUMBER = "1234567890123456";
        private static final String PIN = "1234";
        Bank bank;

        @BeforeEach
        void setUp() {
            bank = new Bank(new BankTest.TestCardGenerator(NUMBER, PIN));
            bank.createAccount();
        }

        @Test
        void returns_logged_account_after_successful_login() {
            bank.login(NUMBER, PIN);

            var loggedAccount = bank.getLoggedAccount();
            assertNotNull(loggedAccount);
        }

        @Test
        void returns_null_after_unsuccessful_login() {
            bank.login(NUMBER, "2222");

            var loggedAccount = bank.getLoggedAccount();
            assertNull(loggedAccount);
        }

        @Test
        void returns_null_if_has_not_logged_in() {
            var loggedAccount = bank.getLoggedAccount();
            assertNull(loggedAccount);
        }
    }

    static class logout {
        private static final String NUMBER = "1234567890123456";
        private static final String PIN = "1234";
        Bank bank;

        @BeforeEach
        void setUp() {
            bank = new Bank(new BankTest.TestCardGenerator(NUMBER, PIN));
            bank.createAccount();
        }

        @Test
        void returns_true_after_successful_logout() {
            bank.login(NUMBER, PIN);

            assertTrue(bank.logout());
            assertFalse(bank.isLogged());
        }

        @Test
        void returns_false_if_not_logged() {
            assertFalse(bank.logout());
        }

        @Test
        void sets_logged_account_to_null() {
            bank.login(NUMBER, PIN);
            bank.logout();
            assertNull(bank.getLoggedAccount());
        }
    }
}