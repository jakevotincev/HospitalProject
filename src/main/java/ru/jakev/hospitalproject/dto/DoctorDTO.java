package ru.jakev.hospitalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jakev.hospitalproject.entities.DoctorSpeciality;

@Data
@NoArgsConstructor
//todo: all args constructor doesnt work
public class DoctorDTO {

    private Integer id;
    private String surname;
    private String name;
    private String middleName;
    private DoctorSpeciality speciality;
    private Integer cabinet;

    public DoctorDTO(Integer id, String surname, String name, String middleName, DoctorSpeciality speciality, Integer cabinet) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.speciality = speciality;
        this.cabinet = cabinet;
    }
}
