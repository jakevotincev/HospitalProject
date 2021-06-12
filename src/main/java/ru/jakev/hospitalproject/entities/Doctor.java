package ru.jakev.hospitalproject.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "hospitals")
@ToString(exclude = "hospitals")
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

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Set<Hospital> hospitals;

}
