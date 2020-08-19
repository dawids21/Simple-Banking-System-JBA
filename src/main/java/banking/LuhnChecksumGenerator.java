package banking;

public class LuhnChecksumGenerator {

    private final String number;

    public LuhnChecksumGenerator(String number) {

        if (number.length() != 15 || !number.chars()
                                            .allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("Number must have 15 digits");
        }
        this.number = number;
    }

    public int generate() {
        //TODO implement generate
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
