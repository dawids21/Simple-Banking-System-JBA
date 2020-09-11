package banking;

import banking.exceptions.AccountNotFoundException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

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

    private static final String IIN = "400000";
    private static final String PIN = "1234";
    private Bank bank;
    private int accountId;
    private AccountsDatabase db;

    @BeforeEach
    void setUp() {
        db = new AccountsDatabase("jdbc:sqlite:test.db");
        bank = new Bank(IIN, new BankTest.TestCardGenerator(IIN, PIN), db);
        accountId = bank.createAccount()
                        .getId();
    }

    @AfterEach
    void tearDown() {
        db.clear();
    }

    @Nested
    class create_account {

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

    @Nested
    class login {

        @Test
        void returns_true_when_successfully_logged_in() {
            assertTrue(bank.login(IIN + String.format("%09d", accountId) + "0", PIN));
            assertTrue(bank.isLogged());
        }

        @Test
        void returns_false_when_account_does_not_exist() {
            assertFalse(bank.login(IIN + "123123123" + "0", "1234"));
            assertFalse(bank.isLogged());
        }

        @Test
        void returns_false_when_card_number_has_not_correct_iin() {
            assertFalse(bank.login("123456" + "000000000" + "0", "1234"));
            assertFalse(bank.isLogged());
        }

        @Test
        void returns_false_when_pin_is_not_correct() {
            assertFalse(bank.login(IIN + String.format("%09d", accountId) + "0", "0000"));
            assertFalse(bank.isLogged());
        }
    }

    @Nested
    class get_logged_account {

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

    @Nested
    class logout {

        @Test
        void returns_true_after_successful_logout() {
            bank.login(IIN + String.format("%09d", accountId) + "0", PIN);

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

    @Nested
    class close_account {

        @Test
        @DisplayName("Deletes account from database")
        void deletes_account_from_database() throws AccountNotFoundException {
            var account = bank.getAccount(accountId);
            bank.closeAccount(account);
            assertFalse(bank.accountExists(account));
        }

        @Test
        @DisplayName("Throws an exception if account does not exist")
        void throw_an_exception_if_account_does_not_exist() {
            var account = new Account(123, new TestCardGenerator(IIN, PIN).generate(123));

            assertThrows(AccountNotFoundException.class,
                         () -> bank.closeAccount(account));
        }
    }
}