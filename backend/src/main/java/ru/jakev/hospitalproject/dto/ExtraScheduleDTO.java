package ru.jakev.hospitalproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ExtraScheduleDTO extends ScheduleDTO {

    private static final long serialVersionUID = -4501702843476798477L;
    @JsonFormat(pattern = "dd_MM_yyyy")
    private LocalDate date;

    public ExtraScheduleDTO(Integer id, Integer doctorId, LocalTime dayStart, LocalTime dayEnd, Duration duration, Integer hospitalId, LocalDate date) {
        super(id, doctorId, dayStart, dayEnd, duration, hospitalId);
        this.date = date;
    }
}
