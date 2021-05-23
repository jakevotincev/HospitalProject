package ru.jakev.hospitalproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ScheduleDTO {

    private Integer id;
    private DoctorDTO doctor;
    private DayOfWeek dayOfWeek;
    private LocalTime dayStart;
    private LocalTime dayEnd;
    private Duration duration;

    public ScheduleDTO(Integer id, DoctorDTO doctor, DayOfWeek dayOfWeek, LocalTime dayStart, LocalTime dayEnd, Duration duration) {
        this.id = id;
        this.doctor = doctor;
        this.dayOfWeek = dayOfWeek;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.duration = duration;
    }
}
