package ru.jakev.hospitalproject.services;

import org.springframework.beans.factory.annotation.Autowired;
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

//todo: write custom entity not found exception???
//todo: maybe write save method
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
    }

    public List<ScheduleDTO> getSchedulesByDoctorId(Integer id) {
        return scheduleRepository.findAllByDoctorId(id).map(scheduleMapper::scheduleToScheduleDto).collect(Collectors.toList());
    }

    public ScheduleDTO getScheduleByDoctorIdAndDayOfWeek(Integer id, DayOfWeek day) throws EntityNotFoundException{
        return scheduleMapper.scheduleToScheduleDto(scheduleRepository
                .findByDoctorIdAndDayOfWeek(id, day).orElseThrow(() ->
                        new EntityNotFoundException("Entity schedule {doctor_id: " + id + ", day: "
                        + day.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + "} not found")));
    }

}
