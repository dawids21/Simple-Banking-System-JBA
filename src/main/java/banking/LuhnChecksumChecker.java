package banking;

public class LuhnChecksumChecker {

    private final String number;

    public LuhnChecksumChecker(String number) {

        if (!number.chars()
                   .allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("Number can have only digits");
        }
        this.number = number;
    }

    public boolean correct() {
        var sum = 0;
        for (int i = 0; i < number.length(); i++) {
            int digit = number.charAt(i) - '0';
            if (i % 2 == 0) {
                digit *= 2;
            }
            if (digit >= 10) {
                digit -= 9;
            }
            sum += digit;
        }

        return sum % 10 == 0;
    }
}
