package ru.jakev.hospitalproject.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.jakev.hospitalproject.dto.ScheduleDTO;
import ru.jakev.hospitalproject.entities.Schedule;
import ru.jakev.hospitalproject.mappers.ScheduleMapper;
import ru.jakev.hospitalproject.repositories.ScheduleRepository;

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
public class ScheduleService {

    private final Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleService(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
    }

    public List<ScheduleDTO> getSchedulesByDoctorId(Integer id) {
        List<ScheduleDTO> scheduleDTOList = scheduleRepository.findAllByDoctorId(id)
                .map(scheduleMapper::scheduleToScheduleDto).collect(Collectors.toList());
        LOGGER.info("found " + scheduleDTOList.size() + " schedules, Doctor.id = " + id);
        return scheduleDTOList;
    }

    public ScheduleDTO getScheduleByDoctorIdAndDayOfWeek(Integer id, DayOfWeek day) throws EntityNotFoundException {
        Schedule schedule = scheduleRepository.findByDoctorIdAndDayOfWeek(id, day).orElseThrow(() ->
                new EntityNotFoundException("Entity schedule {doctor_id: " + id + ", day: "
                        + day.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + "} not found"));
        LOGGER.info("found " + schedule);
        return scheduleMapper.scheduleToScheduleDto(schedule);
    }

}
