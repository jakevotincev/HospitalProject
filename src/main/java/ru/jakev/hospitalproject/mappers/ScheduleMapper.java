package ru.jakev.hospitalproject.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.jakev.hospitalproject.dto.PermanentScheduleDTO;
import ru.jakev.hospitalproject.entities.PermanentSchedule;

@Mapper(componentModel = "spring", uses = {HospitalMapper.class})
public interface ScheduleMapper {

    @Mapping(source = "schedule.doctor.id", target = "doctorId")
    @Mapping(source = "schedule.hospital.id", target = "hospitalId")
    PermanentScheduleDTO scheduleToScheduleDto(PermanentSchedule schedule);

    PermanentSchedule scheduleDtoToSchedule(PermanentScheduleDTO permanentScheduleDTO);


}
