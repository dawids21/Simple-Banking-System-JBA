package banking;

public class Card {

    private final String iin;
    private final String accountID;
    private final String checksum;
    private final String pin;

    public Card(String iin, String accountID, String checksum, String pin) {
        this.iin = iin;
        this.accountID = accountID;
        this.checksum = checksum;
        this.pin = pin;
    }

    public String getNumber() {
        return iin + accountID + checksum;
    }

    public String getPin() {
        return pin;
    }
}
