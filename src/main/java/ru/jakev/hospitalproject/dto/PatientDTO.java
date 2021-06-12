package ru.jakev.hospitalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    private Integer id;
    private String surname;
    private String name;
    private String middleName;
    private Integer age;

}
