package ru.jakev.hospitalproject.mappers;

import org.mapstruct.Mapper;
import ru.jakev.hospitalproject.dto.ScheduleDTO;
import ru.jakev.hospitalproject.entities.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    ScheduleDTO scheduleToScheduleDto(Schedule schedule);

    Schedule scheduleDtoToSchedule(ScheduleDTO scheduleDTO);
}
