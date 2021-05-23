package ru.jakev.hospitalproject.entities;


import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@TypeDef(
        typeClass = PostgreSQLIntervalType.class,
        defaultForType = Duration.class
)
public class Schedule {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    @NotNull
    private Doctor doctor;

    @Column(name = "day_of_week")
    @NotNull
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @NotNull
    @Column(name = "day_start", columnDefinition = "TIME")
    private LocalTime dayStart;

    @NotNull
    @Column(name = "day_end", columnDefinition = "TIME")
    private LocalTime dayEnd;

    @NotNull
    @Column(name = "appointment_duration", columnDefinition = "INTERVAL")
    private Duration duration;

    public Schedule(Integer id, Doctor doctor, DayOfWeek dayOfWeek, LocalTime dayStart, LocalTime dayEnd, Duration duration) {
        this.id = id;
        this.doctor = doctor;
        this.dayOfWeek = dayOfWeek;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.duration = duration;
    }
}
