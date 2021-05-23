package ru.jakev.hospitalproject.entities;

import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@TypeDef(
        typeClass = PostgreSQLIntervalType.class,
        defaultForType = Duration.class
)
public class Appointment {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    @NotNull
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    @NotNull
    private Patient patient;


    @NotNull
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    @NotNull
    @Column(columnDefinition = "INTERVAL")
    private Duration duration;

    public Appointment(Integer id, Doctor doctor, Patient patient, LocalDateTime date, Duration duration) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.duration = duration;
    }
}
