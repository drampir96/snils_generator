package org.example;


import org.example.entity.gender.Female;
import org.example.entity.gender.Male;
import org.example.entity.Person;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MainSnilsGenerator {

    public static void main(String[] args) {

        int poolSize = 10000;
        final String header = "lastName,firstName,patronymic,phone,email,snils,birthday\n";

        Male male = new Male();
        Female female = new Female();


        // Генерируем пул СНИЛСа (distinct)
        Set<String> snilsSet = new TreeSet<>();
        while (snilsSet.size() < poolSize) {
            String snils = Utils.generateSNILS();
            snilsSet.add(snils);
        }
        List<String> snilsList = new ArrayList<>(snilsSet);

        // Генерируем пул телефонов (distinct)
        Set<Long> phoneSet = new TreeSet<>();
        while (phoneSet.size() < poolSize) {
            Long phone = Utils.generatePhoneNumber();
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
                            .birthday(Utils.generateBirthday())
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
                            .birthday(Utils.generateBirthday())
                            .build();
                    fileWriter.append(person.toString());
                }
            }
            fileWriter.flush(); // Очищаем буфер потока
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}