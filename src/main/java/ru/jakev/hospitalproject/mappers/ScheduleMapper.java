package ru.jakev.hospitalproject.mappers;

import org.mapstruct.Mapper;
import ru.jakev.hospitalproject.dto.ScheduleDTO;
import ru.jakev.hospitalproject.entities.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    default ScheduleDTO scheduleToScheduleDto(Schedule schedule) {
        if (schedule == null) {
            return null;
        }
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDoctorId(schedule.getDoctor().getId());
        scheduleDTO.setDayOfWeek(schedule.getDayOfWeek());
        scheduleDTO.setDayStart(schedule.getDayStart());
        scheduleDTO.setDayEnd(schedule.getDayEnd());
        scheduleDTO.setDuration(schedule.getDuration());
        return scheduleDTO;
    }

    ;

    Schedule scheduleDtoToSchedule(ScheduleDTO scheduleDTO);
}
