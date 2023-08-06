package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utils {
    public static long generatePhoneNumber() {
        int min = 1;
        int max = 9999999;
        return 89990000000L + min + (long) (Math.random() * (max - min));
    }

    public static String generateSNILS() {
        Random random = new Random();
        StringBuilder snils = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            int digit = random.nextInt(10);
            snils.append(digit);
        }
        int controlSum = calculateControlSum(snils.toString());
        snils.append(String.format("%02d", controlSum));
        snils.insert(3, "-");
        snils.insert(7, "-");
        snils.insert(11, " ");
        return snils.toString();
    }

    // Метод подсчета контрольной суммы для СНИЛС
    public static int calculateControlSum(String snils) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Integer.parseInt(String.valueOf(snils.charAt(i))) * (9 - i);
        }
        int controlSum = sum % 101;
        if (controlSum == 100 || controlSum == 101) {
            controlSum = 0;
        }
        return controlSum;
    }
    public static String generateBirthday() {
        int minAge = 18 * 365;
        int maxAge = 99 * 365;
        int randomAge = new Random().nextInt(maxAge - minAge + 1) + minAge;
        LocalDate birthDate = LocalDate.now().minusDays(randomAge);
        return birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


}
