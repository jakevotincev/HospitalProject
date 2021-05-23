package ru.jakev.hospitalproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
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

    public Doctor(Integer id, String surname, String name, String middleName, DoctorSpeciality speciality, Integer cabinet) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.speciality = speciality;
        this.cabinet = cabinet;
    }
}
