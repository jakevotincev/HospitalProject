package ru.jakev.hospitalproject.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jakev.hospitalproject.dto.DoctorDTO;
import ru.jakev.hospitalproject.dto.PermanentScheduleDTO;
import ru.jakev.hospitalproject.entities.PermanentSchedule;
import ru.jakev.hospitalproject.mappers.PeopleMapper;
import ru.jakev.hospitalproject.mappers.ScheduleMapper;
import ru.jakev.hospitalproject.repositories.PermanentScheduleRepository;
import ru.jakev.hospitalproject.services.DoctorService;
import ru.jakev.hospitalproject.services.ScheduleService;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
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
    private final DoctorService doctorService;
    private final ScheduleMapper scheduleMapper;
    private final PeopleMapper peopleMapper;

    public ScheduleServiceImpl(PermanentScheduleRepository permanentScheduleRepository, DoctorService doctorService, ScheduleMapper scheduleMapper, PeopleMapper peopleMapper) {
        this.permanentScheduleRepository = permanentScheduleRepository;
        this.doctorService = doctorService;
        this.scheduleMapper = scheduleMapper;
        this.peopleMapper = peopleMapper;
    }

    @Override
    @Transactional
    public List<PermanentScheduleDTO> getSchedulesByDoctorId(Integer id){
        List<PermanentScheduleDTO> permanentScheduleDTOList;
        try (Stream<PermanentSchedule> scheduleStream = permanentScheduleRepository.findAllByDoctorId(id)) {
            permanentScheduleDTOList = scheduleStream.map(scheduleMapper::scheduleToScheduleDto).collect(Collectors.toList());
        }
        LOGGER.info("found " + permanentScheduleDTOList.size() + " schedules, Doctor.id = " + id);
        return permanentScheduleDTOList;
    }

    @Override
    public PermanentScheduleDTO getScheduleByDoctorIdAndDayOfWeek(Integer id, DayOfWeek day) throws EntityNotFoundException {
        PermanentSchedule schedule = permanentScheduleRepository.findByDoctorIdAndDayOfWeek(id, day).orElseThrow(() ->
                new EntityNotFoundException("Entity schedule {doctor_id: " + id + ", day: "
                        + day.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + "} not found"));
        LOGGER.info("found " + schedule);
        return scheduleMapper.scheduleToScheduleDto(schedule);
    }

    //todo: add specific exception
    //todo: add exception if schedules and extra schedules for one day bigger than 1
    @Override
    public PermanentScheduleDTO saveSchedule(PermanentScheduleDTO permanentScheduleDTO) throws Exception {
        PermanentSchedule schedule = scheduleMapper.scheduleDtoToSchedule(permanentScheduleDTO);
        if (permanentScheduleDTO.getDoctorId() == null) throw new Exception();
        else {
            DoctorDTO doctorDTO = doctorService.getDoctorById(permanentScheduleDTO.getDoctorId());
            schedule.setDoctor(peopleMapper.doctorDtoToDoctor(doctorDTO));
            schedule = permanentScheduleRepository.save(schedule);
            LOGGER.info(schedule + " saved");
        }

        return scheduleMapper.scheduleToScheduleDto(schedule);
    }
}
