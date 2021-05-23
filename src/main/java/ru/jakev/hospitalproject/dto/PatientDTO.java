package ru.jakev.hospitalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientDTO {

    private Integer id;
    private String surname;
    private String name;
    private String middleName;
    private Integer age;

    public PatientDTO(Integer id, String surname, String name, String middleName, Integer age) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.age = age;
    }
}
