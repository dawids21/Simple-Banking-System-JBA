package banking;

import java.util.Random;

public class RandomCardGenerator implements CardGenerator {

    public RandomCardGenerator() {
    }

    @Override
    public Card generate(String iin, int accountId) {
        var random = new Random();
        var checksum = random.nextInt(10); //TODO implement Luhn algorithm
        var pin = String.format("%04d", random.nextInt(10000));
        return new Card(iin, String.format("%09d", accountId), String.valueOf(checksum),
                        pin);
    }
}
