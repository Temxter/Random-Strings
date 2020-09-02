package creators;

import model.Entity;

import java.util.Date;
import java.util.Random;

public class EntityRandomCreator {

    private EntityRandomCreator() {
    }

    static public Entity CreateEntity() {
        Date recordDate = createRandomDate();
        String englishText = createRandomEnglishString();
        String russianText = createRandomRussianString();

        Random random = new Random();

        Integer recordInteger = random.nextInt(100_000_000);
        Double recordDouble = (random.nextDouble() * 20.0 + 1.0) % 20.0;
        Entity entity = new Entity(recordDate, englishText, russianText, recordInteger, recordDouble);
        return entity;
    }

    static private Date createRandomDate() {
        long fiveYearsInMs = 1000L*60L*60L*24L*365L*5L;
        Random random = new Random();
        long randomMs = Math.abs(random.nextLong()) % fiveYearsInMs;
        Long dateInMs = System.currentTimeMillis() - randomMs;
        Date date = new Date(dateInMs);
        return date;
    }

    static private String createRandomEnglishString() {
        int leftLimitUpLetter = 65; // letter 'A'
        int rightLimitUpLetter = 90; // letter 'Z'
        int leftLimitDownLetter = 97; // letter 'a'
        int rightLimitDownLetter = 122; // letter 'z'
        int stringLength = 8;
        return  createRandomString(leftLimitUpLetter, rightLimitUpLetter, leftLimitDownLetter, rightLimitDownLetter,
                stringLength);
    }

    static private String createRandomRussianString() {
        int leftLimitUpLetter = 1040; // letter 'А'
        int rightLimitUpLetter = 1071; // letter 'Я'
        int leftLimitDownLetter = 1072; // letter 'а'
        int rightLimitDownLetter = 1103; // letter 'я'
        int stringLength = 8;
        return  createRandomString(leftLimitUpLetter, rightLimitUpLetter, leftLimitDownLetter, rightLimitDownLetter,
                stringLength);
    }

    static private String createRandomString(int leftLimitUpLetter, int rightLimitUpLetter,
                                             int leftLimitDownLetter, int rightLimitDownLetter, int stringLength) {
        Random random = new Random();

        String generatedString = random.ints(leftLimitUpLetter, rightLimitDownLetter)
                .filter(i -> (i >= leftLimitUpLetter && i <= rightLimitUpLetter)
                        || (i >= leftLimitDownLetter && i <= rightLimitDownLetter))
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return  generatedString;
    }
}
