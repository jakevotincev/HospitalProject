package ru.jakev.hospitalproject.services;

import ru.jakev.hospitalproject.dto.PermanentScheduleDTO;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.util.List;


public interface ScheduleService {

    List<PermanentScheduleDTO> getSchedulesByDoctorId(Integer id);

    PermanentScheduleDTO getScheduleByDoctorIdAndDayOfWeek(Integer id, DayOfWeek day) throws EntityNotFoundException;

    PermanentScheduleDTO saveSchedule(PermanentScheduleDTO permanentScheduleDTO) throws Exception;
}
