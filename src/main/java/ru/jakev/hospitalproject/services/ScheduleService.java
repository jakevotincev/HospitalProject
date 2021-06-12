package ru.jakev.hospitalproject.services;

import ru.jakev.hospitalproject.dto.ExtraScheduleDTO;
import ru.jakev.hospitalproject.dto.PermanentScheduleDTO;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;


public interface ScheduleService {

    List<PermanentScheduleDTO> getSchedulesByDoctorIdAndHospitalId(Integer doctorId, Integer hospitalId);

    PermanentScheduleDTO getScheduleByDoctorIdAndDayOfWeekAndHospitalId(Integer doctorId, DayOfWeek day, Integer hospitalId) throws EntityNotFoundException;

    PermanentScheduleDTO savePermanentSchedule(PermanentScheduleDTO permanentScheduleDTO) throws Exception;

    ExtraScheduleDTO saveExtraSchedule(ExtraScheduleDTO extraScheduleDTO) throws Exception;

    ExtraScheduleDTO getScheduleByDateAndHospitalIdAndDoctorId(LocalDate date, Integer hospitalId, Integer doctor_id);
}
