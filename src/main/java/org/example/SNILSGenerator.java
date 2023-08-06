package org.example;


import org.example.entity.gender.Female;
import org.example.entity.gender.Male;
import org.example.entity.Person;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SNILSGenerator {

    public static void main(String[] args) {

        Male male = new Male();
        Female female = new Female();
        final String header = "lastName,firstName,patronymic,phone,email,snils,birthday\n";

        int poolSize = 1000;

        // Генерируем пул СНИЛСа (distinct)
        Set<String> snilsSet = new TreeSet<>();
        while (snilsSet.size() < poolSize) {
            String snils = generateSNILS();
            snilsSet.add(snils);
        }
        List<String> snilsList = new ArrayList<>(snilsSet);

        // Генерируем пул телефонов (distinct)
        Set<Long> phoneSet = new TreeSet<>();
        while (phoneSet.size() < poolSize) {
            Long phone = generatePhoneNumber();
            phoneSet.add(phone);
        }
        List<Long> phoneList = new ArrayList<>(phoneSet);

        try (FileWriter fileWriter = new FileWriter("datapull.csv", false)) {
            fileWriter.append(header);
            for (int i = 0; i < poolSize; i++) {
                if (i % 2 == 0) {
                    Person person = Person.builder()
                            .secondName(male.secondName())
                            .firstName(male.firsName())
                            .patronymic(male.patronymic())
                            .phone(phoneList.get(i))
                            .email("user"+i+"@mail.ru")
                            .snils(snilsList.get(i))
                            .birthday(generateBirthday())
                            .build();
                    fileWriter.append(person.toString());
                } else {
                    Person person = Person.builder()
                            .secondName(female.secondName())
                            .firstName(female.firsName())
                            .patronymic(female.patronymic())
                            .phone(phoneList.get(i))
                            .email("user"+i+"@mail.ru")
                            .snils(snilsList.get(i))
                            .birthday(generateBirthday())
                            .build();
                    fileWriter.append(person.toString());
                }
            }
            fileWriter.flush(); // Очищаем буфер потока
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод генерации номера телефона
    public static long generatePhoneNumber() {
        int min = 1;
        int max = 9999999;
        return 89990000000L + min + (long) (Math.random() * (max - min));
    }

    // Метод генерации номера СНИЛС
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

    // Метод генерации случайной даты рождения
    public static String generateBirthday() {
        int minAge = 18 * 365;
        int maxAge = 99 * 365;
        int randomAge = new Random().nextInt(maxAge - minAge + 1) + minAge;
        LocalDate birthDate = LocalDate.now().minusDays(randomAge);
        String formattedDate = birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return formattedDate;
    }

}