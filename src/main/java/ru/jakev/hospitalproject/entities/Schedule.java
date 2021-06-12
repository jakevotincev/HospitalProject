package ru.jakev.hospitalproject.entities;


import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@TypeDef(
        typeClass = PostgreSQLIntervalType.class,
        defaultForType = Duration.class
)
public abstract class Schedule {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "doctor_id")
    @NotNull
    private Doctor doctor;

    @NotNull
    @Column(name = "day_start", columnDefinition = "TIME")
    private LocalTime dayStart;

    @NotNull
    @Column(name = "day_end", columnDefinition = "TIME")
    private LocalTime dayEnd;

    @NotNull
    @Column(name = "appointment_duration", columnDefinition = "INTERVAL")
    private Duration duration;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

}
