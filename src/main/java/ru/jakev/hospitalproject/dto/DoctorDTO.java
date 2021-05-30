package ru.jakev.hospitalproject.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jakev.hospitalproject.entities.DoctorSpeciality;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    private Integer id;
    private String surname;
    private String name;
    private String middleName;
    private DoctorSpeciality speciality;
    private Integer cabinet;

    @Override
    public String toString() {
        return "DoctorDTO{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", speciality=" + speciality.getName() +
                ", cabinet=" + cabinet +
                '}';
    }
}
