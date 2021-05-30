package ru.jakev.hospitalproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String surname;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DoctorSpeciality speciality;

    private Integer cabinet;

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", speciality=" + speciality.getName() +
                ", cabinet=" + cabinet +
                '}';
    }
}
