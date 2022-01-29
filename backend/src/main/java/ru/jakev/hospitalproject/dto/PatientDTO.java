package ru.jakev.hospitalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO implements Serializable {

    private static final long serialVersionUID = -4679212101638774967L;
    private Integer id;
    private String surname;
    private String name;
    private String middleName;
    private Integer age;

}
