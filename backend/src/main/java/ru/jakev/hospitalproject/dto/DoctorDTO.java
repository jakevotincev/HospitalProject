package ru.jakev.hospitalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jakev.hospitalproject.entities.DoctorSpeciality;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    private Integer id;
    private String surname;
    private String name;
    private String middleName;
    private DoctorSpeciality speciality;
    private Set<HospitalDTO> hospitals;

    @Override
    public String toString() {
        return "DoctorDTO{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", speciality=" + speciality.getName() +
                ", hospitals=" + hospitals +
                '}';
    }
}
