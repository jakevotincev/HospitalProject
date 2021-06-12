package ru.jakev.hospitalproject.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@DiscriminatorValue(value = "permanent")
@ToString
public class PermanentSchedule extends Schedule {
    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    public PermanentSchedule(Integer id, @NotNull Doctor doctor, @NotNull LocalTime dayStart, @NotNull LocalTime dayEnd,
                             @NotNull Duration duration, @NotNull Hospital hospital, DayOfWeek dayOfWeek) {
        super(id, doctor, dayStart, dayEnd, duration, hospital);
        this.dayOfWeek = dayOfWeek;
    }
}
