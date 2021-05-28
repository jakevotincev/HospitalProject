package ru.jakev.hospitalproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd_MM_yyyy HH:mm:ss")
    private LocalDateTime date;
    private Duration duration;

    public AppointmentDTO(Integer id, DoctorDTO doctor, PatientDTO patient, LocalDateTime date) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
    }
}
