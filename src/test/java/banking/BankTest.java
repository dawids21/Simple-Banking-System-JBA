package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    static class TestCardGenerator implements CardGenerator {

        private final String cardIin;
        private final String cardPin;

        public TestCardGenerator(String cardIin, String cardPin) {
            this.cardIin = cardIin;
            this.cardPin = cardPin;
        }

        @Override
        public Card generate(int accountId) {
            return new Card(cardIin + String.format("%09d", accountId) + "0", cardPin);
        }
    }

    static class create_account {

        private static final String IIN = "400000";
        private static final String PIN = "1234";
        Bank bank;
        int accountId;

        @BeforeEach
        void setUp() {
            bank = new Bank(new BankTest.TestCardGenerator(IIN, PIN));
            accountId = bank.createAccount();
        }

        @Test
        void creates_new_account() {
            assertNotNull(bank.getAccount(accountId));
        }

        @Test
        void new_account_has_balance_zero() {
            assertEquals(0, bank.getAccount(accountId)
                                .getBalance());
        }

        @Test
        void new_card_is_generated_when_creating_new_account() {
            assertNotNull(bank.getCard(accountId));
        }
    }

    static class login {

        private static final String IIN = "400000";
        private static final String PIN = "1234";
        Bank bank;
        int accountId;

        @BeforeEach
        void setUp() {
            bank = new Bank(new BankTest.TestCardGenerator(IIN, PIN));
            accountId = bank.createAccount();
        }

        @Test
        void returns_true_when_successfully_logged_in() {
            assertTrue(bank.login(IIN + String.format("%09d", accountId) + "0", PIN));
            assertTrue(bank.isLogged());
        }

        @Test
        void returns_false_when_account_does_not_exist() {
            assertFalse(bank.login("0000000000000000", "1234"));
            assertFalse(bank.isLogged());
        }

        @Test
        void returns_false_when_pin_is_not_correct() {
            assertFalse(bank.login(IIN + String.format("%09d", accountId) + "0", "0000"));
            assertFalse(bank.isLogged());
        }
    }

    static class get_logged_account {

        private static final String IIN = "400000";
        private static final String PIN = "1234";
        Bank bank;
        int accountId;

        @BeforeEach
        void setUp() {
            bank = new Bank(new BankTest.TestCardGenerator(IIN, PIN));
            accountId = bank.createAccount();
        }

        @Test
        void returns_logged_account_after_successful_login() {
            bank.login(IIN + String.format("%09d", accountId) + "0", PIN);

            var loggedAccount = bank.getLoggedAccount();
            assertNotNull(loggedAccount);
        }

        @Test
        void returns_null_after_unsuccessful_login() {
            bank.login(IIN + String.format("%09d", accountId) + "0", "2222");

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

        private static final String IIN = "400000";
        private static final String PIN = "1234";
        Bank bank;
        int accountId;

        @BeforeEach
        void setUp() {
            bank = new Bank(new BankTest.TestCardGenerator(IIN, PIN));
            accountId = bank.createAccount();
        }

        @Test
        void returns_true_after_successful_logout() {
            bank.login(IIN + String.format("%09d", accountId) + "0", "2222");

            assertTrue(bank.logout());
            assertFalse(bank.isLogged());
        }

        @Test
        void returns_false_if_not_logged() {
            assertFalse(bank.logout());
        }

        @Test
        void sets_logged_account_to_null() {
            bank.login(IIN + String.format("%09d", accountId) + "0", "2222");
            bank.logout();
            assertNull(bank.getLoggedAccount());
        }
    }
}