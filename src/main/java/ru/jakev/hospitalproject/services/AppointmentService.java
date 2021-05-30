package ru.jakev.hospitalproject.services;

import ru.jakev.hospitalproject.dto.AppointmentDTO;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;


public interface AppointmentService {

    List<AppointmentDTO> getAppointmentsByDoctorId(Integer id);

    List<AppointmentDTO> getAppointmentsByPatientId(Integer id);

    List<AppointmentDTO> getAppointmentsByDoctorIdAndDateBetween(Integer id, LocalDateTime from, LocalDateTime to);

    Map<LocalTime, Boolean> getScheduleByDoctorIdAndDate(Integer id, LocalDate date) throws EntityNotFoundException;

    AppointmentDTO saveAppointment(AppointmentDTO appointmentDTO) throws EntityNotFoundException;

}
