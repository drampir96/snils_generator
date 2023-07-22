package org.example.entity;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@Builder
public class Person {
    private String lastName;
    private String firstName;
    private String patronymic;
    private Long phone;
    private String email;
    private String snils;
    private String birthday;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(lastName).append(", ")
                .append(firstName).append(", ")
                .append(patronymic).append(", ")
                .append(phone).append(", ")
                .append(email).append(", ")
                .append(snils).append(", ")
                .append(birthday).append("\n");
        return sb.toString();
    }
}
