package ru.jakev.hospitalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class ScheduleDTO {
    private Integer id;
    private Integer doctorId;
    private LocalTime dayStart;
    private LocalTime dayEnd;
    private Duration duration;
    private Integer hospitalId;
}
