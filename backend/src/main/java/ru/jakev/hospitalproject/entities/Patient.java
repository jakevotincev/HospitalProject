package ru.jakev.hospitalproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
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
    private Integer age;

}
