package ru.jakev.hospitalproject.services;

import ru.jakev.hospitalproject.dto.ExtraScheduleDTO;
import ru.jakev.hospitalproject.dto.PermanentScheduleDTO;
import ru.jakev.hospitalproject.exceptions.InvalidScheduleException;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;


public interface ScheduleService {

    List<PermanentScheduleDTO> getSchedulesByDoctorIdAndHospitalId(Integer doctorId, Integer hospitalId);

    PermanentScheduleDTO getScheduleByDoctorIdAndDayOfWeekAndHospitalId(Integer doctorId, DayOfWeek day, Integer hospitalId);

    PermanentScheduleDTO savePermanentSchedule(PermanentScheduleDTO permanentScheduleDTO) throws InvalidScheduleException;

    ExtraScheduleDTO saveExtraSchedule(ExtraScheduleDTO extraScheduleDTO) throws InvalidScheduleException;

    ExtraScheduleDTO getScheduleByDateAndHospitalIdAndDoctorId(LocalDate date, Integer hospitalId, Integer doctor_id);
}
