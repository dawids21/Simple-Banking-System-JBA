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
        var checksum = random.nextInt(10); //TODO implement Luhn algorithm
        var pin = String.format("%04d", random.nextInt(10000));
        return new Card(iin, String.format("%09d", accountId), String.valueOf(checksum),
                        pin);
    }
}
