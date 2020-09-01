package banking;

import java.util.Random;

public class RandomCardGenerator implements CardGenerator {

    private final String iin;

    public RandomCardGenerator(String iin) {
        this.iin = iin;
    }

    @Override
    public Card generate(int accountId) {
        var random = new Random();
        var number = String.format("%09d", accountId);
        var checksum = new LuhnChecksumGenerator(iin + number).generate();
        var pin = String.format("%04d", random.nextInt(10000));

        return new Card(iin + number + checksum, pin);
    }
}
