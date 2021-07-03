package ru.jakev.hospitalproject.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jakev.hospitalproject.dto.AppointmentDTO;
import ru.jakev.hospitalproject.dto.ScheduleDTO;
import ru.jakev.hospitalproject.entities.Appointment;
import ru.jakev.hospitalproject.exceptions.EntityNotFoundException;
import ru.jakev.hospitalproject.exceptions.InvalidAppointmentException;
import ru.jakev.hospitalproject.mappers.AppointmentMapper;
import ru.jakev.hospitalproject.repositories.AppointmentRepository;
import ru.jakev.hospitalproject.services.AppointmentService;
import ru.jakev.hospitalproject.services.ScheduleService;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AppointmentServiceImpl.class);
    private final AppointmentRepository appointmentRepository;
    private final ScheduleService scheduleService;
    private final AppointmentMapper appointmentMapper;


    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, ScheduleService scheduleService,
                                  AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.scheduleService = scheduleService;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByDoctorIdAndHospitalId(Integer doctorId, Integer hospitalId) {
        List<AppointmentDTO> appointmentDTOList;
        try (Stream<Appointment> appointmentStream = appointmentRepository.findAllByDoctorIdAndHospitalId(doctorId, hospitalId)) {
            appointmentDTOList = appointmentStream.map(appointmentMapper::appointmentToAppointmentDto).collect(Collectors.toList());
        }
        LOGGER.info("found " + appointmentDTOList.size() + " appointments, " + "Doctor.Id = " + doctorId +
                " Hospital.Id = " + hospitalId);
        return appointmentDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByPatientId(Integer patientId) {
        List<AppointmentDTO> appointmentDTOList;
        try (Stream<Appointment> appointmentStream = appointmentRepository.findAllByPatientId(patientId)) {
            appointmentDTOList = appointmentStream.map(appointmentMapper::appointmentToAppointmentDto).collect(Collectors.toList());
        }
        LOGGER.info("found " + appointmentDTOList.size() + " appointments, " + "Patient.Id = " + patientId);
        return appointmentDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByDoctorIdAndHospitalIdAndDateBetween(Integer doctorId, Integer hospitalId, LocalDateTime from, LocalDateTime to) {
        List<AppointmentDTO> appointmentDTOList;
        try (Stream<Appointment> appointmentStream = appointmentRepository.findAllByDoctorIdAndHospitalIdAndDateBetween(doctorId, hospitalId, from, to)) {
            appointmentDTOList = appointmentStream.map(appointmentMapper::appointmentToAppointmentDto).collect(Collectors.toList());
        }
        LOGGER.info("found " + appointmentDTOList.size() + " appointments, " + "Doctor.Id = " + doctorId + " Hospital.Id = " +
                hospitalId + " between " + from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " and " +
                to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return appointmentDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByDoctorIdAndDateBetween(Integer doctorId, LocalDateTime from, LocalDateTime to) {
        List<AppointmentDTO> appointmentDTOList;
        try (Stream<Appointment> appointmentStream = appointmentRepository.findAllByDoctorIdAndDateBetween(doctorId, from, to)) {
            appointmentDTOList = appointmentStream.map(appointmentMapper::appointmentToAppointmentDto).collect(Collectors.toList());
        }
        LOGGER.info("found " + appointmentDTOList.size() + " appointments, " + "Doctor.Id = " + doctorId
                + " between " + from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " and " +
                to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return appointmentDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<LocalTime, Boolean> getScheduleByDoctorIdAndDateAndHospitalId(Integer doctorId, LocalDate date, Integer hospitalId) {
        ScheduleDTO schedule = scheduleService.getScheduleByDateAndHospitalIdAndDoctorId(date, hospitalId, doctorId);
        if (schedule == null)
            schedule = scheduleService.getScheduleByDoctorIdAndDayOfWeekAndHospitalId(doctorId, date.getDayOfWeek(), hospitalId);
        Map<LocalTime, Boolean> result = new LinkedHashMap<>();
        LocalDateTime dayStart = date.atTime(schedule.getDayStart()).isAfter(LocalDateTime.now()) ? date.atTime(schedule.getDayStart()) :
                LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(1);
        LocalDateTime dayEnd = date.atTime(schedule.getDayEnd());
        List<AppointmentDTO> appointments = appointmentRepository.findAllByDoctorIdAndHospitalIdAndDateBetween(doctorId, hospitalId, dayStart, dayEnd)
                .map(appointmentMapper::appointmentToAppointmentDto).collect(Collectors.toList());
        LOGGER.info("found " + appointments.size() + " appointments " + "doctorId: " + doctorId + " hospitalId: " + hospitalId + "\n"
                + dayStart + " - " + dayEnd);
        for (; dayStart.isBefore(dayEnd); dayStart = dayStart.plusHours(schedule.getDuration().toHours())) {
            boolean isTimeBusy = !isAppointmentListContainsDate(appointments, dayStart);
            result.put(dayStart.toLocalTime(), isTimeBusy);
        }
        LOGGER.info("generated schedule for Doctor.id = " + doctorId + " Date: " + date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        return result;
    }

    @Override
    public AppointmentDTO saveAppointment(AppointmentDTO appointmentDTO) throws InvalidAppointmentException {
        Integer doctorId = appointmentDTO.getDoctor().getId();
        Integer hospitalId = appointmentDTO.getHospital().getId();
        DayOfWeek day = appointmentDTO.getDate().getDayOfWeek();
        Duration duration;
        try {
            duration = scheduleService.getScheduleByDoctorIdAndDayOfWeekAndHospitalId(doctorId, day, hospitalId).getDuration();
        } catch (EntityNotFoundException e){
            throw new InvalidAppointmentException(e, appointmentDTO);
        }
        appointmentDTO.setDuration(duration);
        Appointment appointment = appointmentRepository.save(appointmentMapper.appointmentDtoToAppointment(appointmentDTO));
        LOGGER.info(appointment + " saved");
        return appointmentMapper.appointmentToAppointmentDto(appointment);
    }

    private boolean isAppointmentListContainsDate(List<AppointmentDTO> list, LocalDateTime date) {
        return list.stream().anyMatch(appointment -> appointment.getDate().equals(date));
    }
}
