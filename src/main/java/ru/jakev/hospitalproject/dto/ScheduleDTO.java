package ru.jakev.hospitalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {

    private Integer id;
    private Integer doctorId;
    private DayOfWeek dayOfWeek;
    private LocalTime dayStart;
    private LocalTime dayEnd;
    private Duration duration;

}
