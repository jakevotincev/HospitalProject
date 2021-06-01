package ru.jakev.hospitalproject.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.jakev.hospitalproject.dto.ScheduleDTO;
import ru.jakev.hospitalproject.entities.Schedule;

@Mapper(componentModel = "spring", uses = {HospitalMapper.class})
public interface ScheduleMapper {

    @Mapping(source = "schedule.doctor.id", target = "doctorId")
    @Mapping(source = "schedule.hospital.id", target = "hospitalId")
    ScheduleDTO scheduleToScheduleDto(Schedule schedule);

    Schedule scheduleDtoToSchedule(ScheduleDTO scheduleDTO);
}
