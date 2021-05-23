package ru.jakev.hospitalproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AppointmentDTO {

    private Integer id;
    private DoctorDTO doctor;
    private PatientDTO patient;
    private LocalDateTime date;
    private Duration duration;

}
