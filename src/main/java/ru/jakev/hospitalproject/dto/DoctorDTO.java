package ru.jakev.hospitalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jakev.hospitalproject.entities.DoctorSpeciality;

@Data
@NoArgsConstructor
@AllArgsConstructor
//todo: all args constructor doesnt work
public class DoctorDTO {

    private Integer id;
    private String surname;
    private String name;
    private String middleName;
    private DoctorSpeciality speciality;
    private Integer cabinet;

}
