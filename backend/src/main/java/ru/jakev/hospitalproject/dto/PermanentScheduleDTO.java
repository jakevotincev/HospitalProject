package ru.jakev.hospitalproject.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class PermanentScheduleDTO extends ScheduleDTO  {

    private static final long serialVersionUID = -3718292003407643434L;
    private DayOfWeek dayOfWeek;

    public PermanentScheduleDTO(Integer id, Integer doctorId, LocalTime dayStart, LocalTime dayEnd, Duration duration, Integer hospitalId, DayOfWeek dayOfWeek) {
        super(id, doctorId, dayStart, dayEnd, duration, hospitalId);
        this.dayOfWeek = dayOfWeek;
    }

}
