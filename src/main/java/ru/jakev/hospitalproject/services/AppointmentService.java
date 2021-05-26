package ru.jakev.hospitalproject.services;

import org.springframework.stereotype.Service;
import ru.jakev.hospitalproject.dto.AppointmentDTO;
import ru.jakev.hospitalproject.dto.ScheduleDTO;
import ru.jakev.hospitalproject.entities.Appointment;
import ru.jakev.hospitalproject.mappers.AppointmentMapper;
import ru.jakev.hospitalproject.repositories.AppointmentRepository;

import javax.persistence.EntityNotFoundException;
import java.time.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ScheduleService scheduleService;
    private final AppointmentMapper appointmentMapper;


    public AppointmentService(AppointmentRepository appointmentRepository, ScheduleService scheduleService,
                              AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.scheduleService = scheduleService;
        this.appointmentMapper = appointmentMapper;
    }

    public List<AppointmentDTO> getAppointmentsByDoctorId(Integer id) {
        return appointmentRepository.findAllByDoctorId(id).map(appointmentMapper::appointmentToAppointmentDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsByPatientId(Integer id) {
        return appointmentRepository.findAllByPatientId(id).map(appointmentMapper::appointmentToAppointmentDto)
                .collect(Collectors.toList());
    }

    //todo: включительно или нет, если нет то проблема
    public List<AppointmentDTO> getAppointmentsByDoctorIdAndDateBetween(Integer id, LocalDateTime from,
                                                                        LocalDateTime to) {
        return appointmentRepository.findAllByDoctorIdAndDateBetween(id, from, to)
                .map(appointmentMapper::appointmentToAppointmentDto).collect(Collectors.toList());
    }

    public Map<LocalTime, Boolean> getScheduleByDoctorIdAndDate(Integer id, LocalDate date) {
        ScheduleDTO schedule = scheduleService.getScheduleByDoctorIdAndDayOfWeek(id, date.getDayOfWeek());
        Map<LocalTime, Boolean> result = new HashMap<>();
        LocalDateTime dayStart = date.atTime(schedule.getDayStart());
        LocalDateTime dayEnd = date.atTime(schedule.getDayEnd());
        List<AppointmentDTO> appointments = appointmentRepository.findAllByDoctorIdAndDateBetween(id, dayStart, dayEnd)
                .map(appointmentMapper::appointmentToAppointmentDto).collect(Collectors.toList());
        for (; dayStart.isBefore(dayEnd); dayStart = dayStart.plusHours(schedule.getDuration().toHours())) {
            boolean isTimeBusy = isAppointmentListContainsDate(appointments, dayStart);
            result.put(dayStart.toLocalTime(), isTimeBusy);
        }
        return result;
    }

    //todo: add duration check?
    public AppointmentDTO saveAppointment(AppointmentDTO appointment) throws EntityNotFoundException {
        Integer doctorId = appointment.getDoctor().getId();
        DayOfWeek day = appointment.getDate().getDayOfWeek();
        Duration duration = scheduleService.getScheduleByDoctorIdAndDayOfWeek(doctorId, day).getDuration();
        appointment.setDuration(duration);
        return appointmentMapper.appointmentToAppointmentDto(appointmentRepository
                .save(appointmentMapper.appointmentDtoToAppointment(appointment)));
    }

    private boolean isAppointmentListContainsDate(List<AppointmentDTO> list, LocalDateTime date) {
        return list.stream().anyMatch(appointment -> appointment.getDate().equals(date));
    }
}
