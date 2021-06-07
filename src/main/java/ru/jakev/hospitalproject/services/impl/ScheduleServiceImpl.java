package ru.jakev.hospitalproject.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jakev.hospitalproject.dto.DoctorDTO;
import ru.jakev.hospitalproject.dto.ExtraScheduleDTO;
import ru.jakev.hospitalproject.dto.PermanentScheduleDTO;
import ru.jakev.hospitalproject.entities.ExtraSchedule;
import ru.jakev.hospitalproject.entities.PermanentSchedule;
import ru.jakev.hospitalproject.mappers.PeopleMapper;
import ru.jakev.hospitalproject.mappers.ScheduleMapper;
import ru.jakev.hospitalproject.repositories.ExtraScheduleRepository;
import ru.jakev.hospitalproject.repositories.PermanentScheduleRepository;
import ru.jakev.hospitalproject.services.DoctorService;
import ru.jakev.hospitalproject.services.ScheduleService;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//todo: write custom entity not found exception???
//todo: maybe write save method
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ScheduleServiceImpl.class);
    private final PermanentScheduleRepository permanentScheduleRepository;
    private final ExtraScheduleRepository extraScheduleRepository;
    private final DoctorService doctorService;
    private final ScheduleMapper scheduleMapper;
    private final PeopleMapper peopleMapper;

    public ScheduleServiceImpl(PermanentScheduleRepository permanentScheduleRepository, ExtraScheduleRepository extraScheduleRepository,
                               DoctorService doctorService, ScheduleMapper scheduleMapper, PeopleMapper peopleMapper) {
        this.permanentScheduleRepository = permanentScheduleRepository;
        this.extraScheduleRepository = extraScheduleRepository;
        this.doctorService = doctorService;
        this.scheduleMapper = scheduleMapper;
        this.peopleMapper = peopleMapper;
    }

    @Override
    @Transactional
    public List<PermanentScheduleDTO> getSchedulesByDoctorIdAndHospitalId(Integer doctorId, Integer hospitalId) {
        List<PermanentScheduleDTO> permanentScheduleDTOList;
        try (Stream<PermanentSchedule> scheduleStream = permanentScheduleRepository.findAllByDoctorIdAAndHospitalId(doctorId, hospitalId)) {
            permanentScheduleDTOList = scheduleStream.map(scheduleMapper::permanentScheduleToPermanentScheduleDto).collect(Collectors.toList());
        }
        LOGGER.info("found " + permanentScheduleDTOList.size() + " schedules, Doctor.id = " + doctorId +
                " Hospital.Id = " + hospitalId);
        return permanentScheduleDTOList;
    }

    @Override
    @Transactional
    public PermanentScheduleDTO getScheduleByDoctorIdAndDayOfWeekAndHospitalId(Integer doctorId, DayOfWeek day, Integer hospitalId) throws EntityNotFoundException {
        PermanentSchedule schedule = permanentScheduleRepository.findByDoctorIdAndDayOfWeekAndHospitalId(doctorId, day, hospitalId).orElseThrow(() ->
                new EntityNotFoundException("Entity schedule {doctor_id: " + doctorId + " hospital_id: "
                        + hospitalId + ", day: " + day.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + "} not found"));
        LOGGER.info("found " + schedule);
        return scheduleMapper.permanentScheduleToPermanentScheduleDto(schedule);
    }

    @Override
    @Transactional
    public ExtraScheduleDTO getScheduleByDateAndHospitalIdAndDoctorId(LocalDate date, Integer hospitalId, Integer doctor_id) {
        ExtraSchedule schedule = extraScheduleRepository.getByDateAndHospitalIdAndDoctorId(date, hospitalId, doctor_id).orElse(null);
        if (schedule == null)
            LOGGER.info("No extra schedules found");
        else
            LOGGER.info("found " + schedule + " Date = " + date.format(DateTimeFormatter.ISO_LOCAL_DATE)
                    + " Doctor.Id = " + doctor_id + " Hospital.Id = " + hospitalId);
        return scheduleMapper.extraScheduleToExtraScheduleDto(schedule);
    }

    //todo: add specific exception
    //todo: add exception if schedules and extra schedules for one day bigger than 1
    @Override
    public PermanentScheduleDTO savePermanentSchedule(PermanentScheduleDTO permanentScheduleDTO) throws Exception {
        if (permanentScheduleDTO.getDoctorId() == null) throw new Exception();
        DoctorDTO doctorDTO = doctorService.getDoctorById(permanentScheduleDTO.getDoctorId());
        PermanentSchedule schedule = scheduleMapper.permanentScheduleDtoToPermanentSchedule(permanentScheduleDTO);
        schedule.setDoctor(peopleMapper.doctorDtoToDoctor(doctorDTO));
        schedule = permanentScheduleRepository.save(schedule);
        LOGGER.info(schedule + " saved");
        return scheduleMapper.permanentScheduleToPermanentScheduleDto(schedule);


    }

    @Override
    public ExtraScheduleDTO saveExtraSchedule(ExtraScheduleDTO extraScheduleDTO) throws Exception {
        if (extraScheduleDTO.getDoctorId() == null) throw new Exception();
        DoctorDTO doctorDTO = doctorService.getDoctorById(extraScheduleDTO.getDoctorId());
        ExtraSchedule schedule = scheduleMapper.extraScheduleDtoToExtraSchedule(extraScheduleDTO);
        schedule.setDoctor(peopleMapper.doctorDtoToDoctor(doctorDTO));
        schedule = extraScheduleRepository.save(schedule);
        LOGGER.info(schedule + " saved");
        return scheduleMapper.extraScheduleToExtraScheduleDto(schedule);
    }
}
