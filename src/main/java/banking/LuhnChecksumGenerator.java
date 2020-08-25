package banking;

public class LuhnChecksumGenerator {

    private final String number;

    public LuhnChecksumGenerator(String number) {

        if (!number.chars()
                   .allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("Number can have only digits");
        }
        this.number = number;
    }

    public int generate() {
        var sum = 0;
        for (int i = 0; i < number.length(); i++) {
            int digit = number.charAt(i) - '0';
            if (i % 2 == 0) {
                digit *= 2;
            }
            if (digit > 10) {
                digit -= 9;
            }
            sum += digit;
        }

        return sum % 10 == 0 ? 0 : 10 - (sum % 10);
    }
}
