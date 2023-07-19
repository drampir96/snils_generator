package org.example.entity;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@Builder
public class Person {
    private String fio;
    private Long phone;
    private String email;
    private String snils;
    private String birthday;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(fio).append(", ")
          .append(phone).append(", ")
          .append(email).append(", ")
          .append(snils).append(", ")
          .append(birthday).append("\n");
        return sb.toString();
    }
}
