package ru.jakev.hospitalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {

    private Integer id;
    private DoctorDTO doctor;
    private PatientDTO patient;
    private LocalDateTime date;
    private Duration duration;

    public AppointmentDTO(Integer id, DoctorDTO doctor, PatientDTO patient, LocalDateTime date) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
    }
}
