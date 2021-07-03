package ru.jakev.hospitalproject.services;

import org.springframework.transaction.annotation.Transactional;
import ru.jakev.hospitalproject.dto.AppointmentDTO;
import ru.jakev.hospitalproject.exceptions.InvalidAppointmentException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;


public interface AppointmentService {

    List<AppointmentDTO> getAppointmentsByDoctorIdAndHospitalId(Integer doctorId, Integer hospitalId);

    List<AppointmentDTO> getAppointmentsByPatientId(Integer patientId);

    List<AppointmentDTO> getAppointmentsByDoctorIdAndHospitalIdAndDateBetween(Integer doctorId, Integer hospitalId, LocalDateTime from, LocalDateTime to);

    List<AppointmentDTO> getAppointmentsByDoctorIdAndDateBetween(Integer doctorId, LocalDateTime from, LocalDateTime to);

    Map<LocalTime, Boolean> getScheduleByDoctorIdAndDateAndHospitalId(Integer doctorId, LocalDate date, Integer hospitalId);

    AppointmentDTO saveAppointment(AppointmentDTO appointmentDTO) throws InvalidAppointmentException;

}
