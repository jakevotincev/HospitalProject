package ru.jakev.hospitalproject.services;

import ru.jakev.hospitalproject.dto.ScheduleDTO;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.util.List;


public interface ScheduleService {

    List<ScheduleDTO> getSchedulesByDoctorId(Integer id);

    ScheduleDTO getScheduleByDoctorIdAndDayOfWeek(Integer id, DayOfWeek day) throws EntityNotFoundException;


    ScheduleDTO saveSchedule(ScheduleDTO scheduleDTO) throws Exception;
}
