package ru.jakev.hospitalproject.entities;

import lombok.*;

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
@EqualsAndHashCode(exclude="doctors")
@ToString(exclude = "doctors")
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
