package ru.jakev.hospitalproject.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.jakev.hospitalproject.dto.AppointmentDTO;
import ru.jakev.hospitalproject.dto.ScheduleDTO;
import ru.jakev.hospitalproject.entities.Appointment;
import ru.jakev.hospitalproject.mappers.AppointmentMapper;
import ru.jakev.hospitalproject.repositories.AppointmentRepository;

import javax.persistence.EntityNotFoundException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final Logger LOGGER = LoggerFactory.getLogger(AppointmentService.class);
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
        List<AppointmentDTO> appointmentDTOList = appointmentRepository.findAllByDoctorId(id)
                .map(appointmentMapper::appointmentToAppointmentDto).collect(Collectors.toList());
        LOGGER.info("found " + appointmentDTOList.size() + " appointments, " + "Doctor.id = " + id);
        return appointmentDTOList;
    }

    public List<AppointmentDTO> getAppointmentsByPatientId(Integer id) {
        List<AppointmentDTO> appointmentDTOList = appointmentRepository.findAllByPatientId(id)
                .map(appointmentMapper::appointmentToAppointmentDto).collect(Collectors.toList());
        LOGGER.info("found " + appointmentDTOList.size() + " appointments, " + "Patient.id = " + id);
        return appointmentDTOList;
    }

    //todo: включительно или нет, если нет то проблема
    public List<AppointmentDTO> getAppointmentsByDoctorIdAndDateBetween(Integer id, LocalDateTime from, LocalDateTime to) {
        List<AppointmentDTO> appointmentDTOList = appointmentRepository.findAllByDoctorIdAndDateBetween(id, from, to)
                .map(appointmentMapper::appointmentToAppointmentDto).collect(Collectors.toList());
        LOGGER.info("found " + appointmentDTOList.size() + " appointments, " + "Patient.id = " + id +
                " between " + from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " and " +
                to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return appointmentDTOList;
    }

    //todo: ask about throws
    public Map<LocalTime, Boolean> getScheduleByDoctorIdAndDate(Integer id, LocalDate date) throws EntityNotFoundException {
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
        LOGGER.info("found schedule for Doctor.id = " + id + " Date: " + date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        return result;
    }

    //todo: add duration check?
    public AppointmentDTO saveAppointment(AppointmentDTO appointmentDTO) throws EntityNotFoundException {
        Integer doctorId = appointmentDTO.getDoctor().getId();
        DayOfWeek day = appointmentDTO.getDate().getDayOfWeek();
        Duration duration = scheduleService.getScheduleByDoctorIdAndDayOfWeek(doctorId, day).getDuration();
        appointmentDTO.setDuration(duration);
        Appointment appointment = appointmentRepository.save(appointmentMapper.appointmentDtoToAppointment(appointmentDTO));
        LOGGER.info(appointment + " saved");
        return appointmentMapper.appointmentToAppointmentDto(appointment);
    }

    private boolean isAppointmentListContainsDate(List<AppointmentDTO> list, LocalDateTime date) {
        return list.stream().anyMatch(appointment -> appointment.getDate().equals(date));
    }
}
