package banking;

import banking.exceptions.BankException;
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
            var accountIdAsString = String.format("%09d", accountId);
            var checksum = new LuhnChecksumGenerator(iin + accountIdAsString).generate();
            return new Card(iin + accountIdAsString + checksum, cardPin);
        }
    }

    private static final String IIN = "400000";
    private static final String PIN = "1234";
    private Bank bank;
    private int accountId;
    private AccountsDatabase db;

    @BeforeEach
    void setUp() throws BankException {
        db = new AccountsDatabase("jdbc:sqlite:test.db");
        bank = new Bank(IIN, new BankTest.TestCardGenerator(IIN, PIN), db);
        accountId = bank.createAccount();
    }

    @AfterEach
    void tearDown() {
        db.clear();
    }

    @Nested
    class create_account {

        private Account account;

        @BeforeEach
        void setUp() {
            try {
                account = bank.getAccount(accountId);
            } catch (BankException e) {
                account = null;
            }
        }

        @Test
        void creates_new_account() {
            assertNotNull(account);
        }

        @Test
        void new_account_has_balance_zero() {
            assertEquals(0, account.getBalance());
        }

        @Test
        void new_card_is_generated_when_creating_new_account() {
            assertDoesNotThrow(() -> bank.getCard(accountId));
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
        void deletes_account_from_database() throws BankException {
            var account = bank.getAccount(accountId);
            bank.closeAccount(account);
            assertFalse(bank.accountExists(account));
        }

        @Test
        @DisplayName("Throws an exception if account does not exist")
        void throw_an_exception_if_account_does_not_exist() {
            var account = new Account(123, new TestCardGenerator(IIN, PIN).generate(123));

            assertThrows(BankException.class, () -> bank.closeAccount(account));
        }
    }

    @Nested
    class checkCardNumbersForTransfer {

        private String originAccountNumber;
        private String destinationAccountNumber;

        @BeforeEach
        void setUp() throws BankException {
            originAccountNumber = bank.getAccount(accountId)
                                      .getCard()
                                      .getNumber();
            destinationAccountNumber = bank.getAccount(bank.createAccount())
                                           .getCard()
                                           .getNumber();
        }

        @Test
        @DisplayName("throws an BankException with message \"Such card does " +
                     "not exist.\" if the destination account does not exist")
        void throws_an_bank_exception_with_message_such_card_does_not_exist_if_the_destination_account_does_not_exist() {
            var thrownException = assertThrows(BankException.class,
                                               () -> bank.checkCardNumbersForTransfer(
                                                        originAccountNumber,
                                                        IIN + "1234567899"));
            assertEquals("Such card does not exist.", thrownException.getMessage());
        }

        @Test
        @DisplayName("throws a BankException with message \"You can't transfer money " +
                     "to the same account!\" when transfer to the same account")
        void throws_a_bank_exception_with_message_you_can_t_transfer_money_to_the_same_account_when_transfer_to_the_same_account() {
            var thrownException = assertThrows(BankException.class,
                                               () -> bank.checkCardNumbersForTransfer(
                                                        originAccountNumber,
                                                        originAccountNumber));
            assertEquals("You can't transfer money to the same account!",
                         thrownException.getMessage());
        }

        @Test
        @DisplayName("throws a BankException with message \"Probably you made mistake " +
                     "in the card number. Please try again!\" when destination account " +
                     "does not pass Luhn algorithm")
        void throws_a_bank_exception_with_message_probably_you_made_mistake_in_the_card_number_please_try_again_when_destination_account_does_not_pass_luhn_algorithm() {
            var thrownException = assertThrows(BankException.class,
                                               () -> bank.checkCardNumbersForTransfer(
                                                        originAccountNumber,
                                                        IIN + "1234567891"));
            assertEquals(
                     "Probably you made mistake in the card number. Please try again!",
                     thrownException.getMessage());
        }

        @Test
        @DisplayName("does not throw an exception when everything is ok")
        void does_not_throw_an_exception_when_everything_is_ok() {
            assertDoesNotThrow(() -> bank.checkCardNumbersForTransfer(originAccountNumber,
                                                                      destinationAccountNumber));
        }
    }

    @Nested
    class transfer {

        private int destinationAccountId;
        private String destinationAccountNumber;

        @BeforeEach
        void setUp() throws BankException {
            var originAccount = bank.getAccount(accountId);
            originAccount.setBalance(10000);
            bank.updateAccount(originAccount);
            destinationAccountId = bank.createAccount();
            destinationAccountNumber = bank.getAccount(destinationAccountId)
                                           .getCard()
                                           .getNumber();
        }

        @Test
        @DisplayName("throws a BankException with message \"Not enough money!\" " +
                     "if the origin account does not have enough money")
        void throws_a_bank_exception_with_message_not_enough_money_if_the_origin_account_does_not_have_enough_money() {
            var thrownException = assertThrows(BankException.class,
                                               () -> bank.transfer(accountId,
                                                                   destinationAccountNumber,
                                                                   20000));
            assertEquals("Not enough money!", thrownException.getMessage());
        }

        @Test
        @DisplayName("transfers money from one account to another")
        void transfers_money_from_one_account_to_another() throws BankException {
            bank.transfer(accountId, destinationAccountNumber, 10000);
            assertEquals(0, bank.getAccount(accountId)
                                .getBalance());
            assertEquals(10000, bank.getAccount(destinationAccountId)
                                    .getBalance());
        }
    }
}