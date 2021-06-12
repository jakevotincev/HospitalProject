package ru.jakev.hospitalproject.services;

import org.springframework.transaction.annotation.Transactional;
import ru.jakev.hospitalproject.dto.AppointmentDTO;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;


public interface AppointmentService {

    @Transactional
    List<AppointmentDTO> getAppointmentsByDoctorIdAndHospitalId(Integer doctorId, Integer hospitalId);

    @Transactional
    List<AppointmentDTO> getAppointmentsByPatientId(Integer patientId);

    @Transactional
    List<AppointmentDTO> getAppointmentsByDoctorIdAndHospitalIdAndDateBetween(Integer doctorId, Integer hospitalId, LocalDateTime from, LocalDateTime to);

    @Transactional
    Map<LocalTime, Boolean> getScheduleByDoctorIdAndDateAndHospitalId(Integer doctorId, LocalDate date, Integer hospitalId) throws EntityNotFoundException;

    AppointmentDTO saveAppointment(AppointmentDTO appointmentDTO) throws EntityNotFoundException;

}
