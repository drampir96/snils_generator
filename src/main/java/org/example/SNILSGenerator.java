package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SNILSGenerator {

    public static void main(String[] args) {

        // Генерируем пул СНИЛСа
        Set<String> treeSet = new TreeSet<>();
        while (treeSet.size() < 10) {
            String sn = generateSNILS();
            treeSet.add(sn);
        }

        // Записываем сгенерированные данные в файл
        try (FileWriter fileWriter = new FileWriter("datapull.csv", false)) {
            for (String snils : treeSet) {
                fileWriter.append(snils).append("\n"); // Записываем строку в файл
            }
            fileWriter.flush(); // Очищаем буфер потока
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static String generateSNILS() {
        Random random = new Random();
        StringBuilder snils = new StringBuilder();

        // Генерируем первые 9 цифр СНИЛСа
        for (int i = 0; i < 9; i++) {
            int digit = random.nextInt(10);
            snils.append(digit);
        }

        // Рассчитываем контрольное число
        int controlSum = calculateControlSum(snils.toString());

        // Добавляем контрольное число в СНИЛС
        snils.append(String.format("%02d", controlSum));

        // Форматируем СНИЛС в виде xxx-xxx-xxx xx
        snils.insert(3, "-");
        snils.insert(7, "-");
        snils.insert(11, " ");

        return snils.toString();
    }

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
}