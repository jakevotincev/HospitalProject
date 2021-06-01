package ru.jakev.hospitalproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {

    @Id
    private Integer id;

    @NotNull
    private String name;
    @NotNull
    private String address;

    @ManyToMany(mappedBy = "hospitals", fetch = FetchType.LAZY)
    private Set<Doctor> doctors;
}
