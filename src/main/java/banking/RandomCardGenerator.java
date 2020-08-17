package banking;

import java.util.HashSet;
import java.util.Random;

public class RandomCardGenerator implements CardGenerator {

    private final String iin;

    private final HashSet<Card> generatedCards = new HashSet<>();

    public RandomCardGenerator(String iin) {
        this.iin = iin;
    }

    @Override
    public Card generate() {
        Card card;
        var random = new Random();
        do {
            var accountID = String.format("%09d", random.nextInt(1000000000));
            var checksum = random.nextInt(10); //TODO implement Luhn algorithm
            var pin = String.format("%04d", random.nextInt(10000));
            card = new Card(iin, accountID, String.valueOf(checksum), pin);
        } while (generatedCards.contains(card));
        generatedCards.add(card);
        return card;
    }
}
