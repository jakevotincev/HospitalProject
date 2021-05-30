package ru.jakev.hospitalproject.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jakev.hospitalproject.dto.DoctorDTO;
import ru.jakev.hospitalproject.dto.ScheduleDTO;
import ru.jakev.hospitalproject.entities.Schedule;
import ru.jakev.hospitalproject.mappers.PeopleMapper;
import ru.jakev.hospitalproject.mappers.ScheduleMapper;
import ru.jakev.hospitalproject.repositories.ScheduleRepository;
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

    private final static Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleRepository scheduleRepository;
    private final DoctorService doctorService;
    private final ScheduleMapper scheduleMapper;
    private final PeopleMapper peopleMapper;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, DoctorService doctorService, ScheduleMapper scheduleMapper, PeopleMapper peopleMapper) {
        this.scheduleRepository = scheduleRepository;
        this.doctorService = doctorService;
        this.scheduleMapper = scheduleMapper;
        this.peopleMapper = peopleMapper;
    }
    @Override
    @Transactional
    public List<ScheduleDTO> getSchedulesByDoctorId(Integer id) {
        List<ScheduleDTO> scheduleDTOList;
        try (Stream<Schedule> scheduleStream = scheduleRepository.findAllByDoctorId(id)) {
            scheduleDTOList = scheduleStream.map(scheduleMapper::scheduleToScheduleDto).collect(Collectors.toList());
        }
        LOGGER.info("found " + scheduleDTOList.size() + " schedules, Doctor.id = " + id);
        return scheduleDTOList;
    }

    @Override
    public ScheduleDTO getScheduleByDoctorIdAndDayOfWeek(Integer id, DayOfWeek day) throws EntityNotFoundException {
        Schedule schedule = scheduleRepository.findByDoctorIdAndDayOfWeek(id, day).orElseThrow(() ->
                new EntityNotFoundException("Entity schedule {doctor_id: " + id + ", day: "
                        + day.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + "} not found"));
        LOGGER.info("found " + schedule);
        return scheduleMapper.scheduleToScheduleDto(schedule);
    }

    //todo: add specific exception
    @Override
    public ScheduleDTO saveSchedule(ScheduleDTO scheduleDTO) throws Exception {
        Schedule schedule = scheduleMapper.scheduleDtoToSchedule(scheduleDTO);
        if (scheduleDTO.getDoctorId() == null) throw new Exception();
        else {
            DoctorDTO doctorDTO = doctorService.getDoctorById(scheduleDTO.getDoctorId());
            schedule.setDoctor(peopleMapper.doctorDtoToDoctor(doctorDTO));
            schedule = scheduleRepository.save(schedule);
            LOGGER.info(schedule + " saved");
        }

        return scheduleMapper.scheduleToScheduleDto(schedule);
    }
}
