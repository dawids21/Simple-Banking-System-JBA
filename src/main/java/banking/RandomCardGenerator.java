package banking;

import java.util.Random;

public class RandomCardGenerator implements CardGenerator {

    public RandomCardGenerator() {
    }

    @Override
    public Card generate(String iin, int accountId) {
        var strAccountId = String.format("%09d", accountId);
        var checksum = new LuhnChecksumGenerator(iin + strAccountId).generate();
        var pin = String.format("%04d", new Random().nextInt(10000));

        return new Card(iin, strAccountId, String.valueOf(checksum), pin);
    }
}
