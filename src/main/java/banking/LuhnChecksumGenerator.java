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
        //TODO implement generate
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
