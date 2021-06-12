package ru.jakev.hospitalproject.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.jakev.hospitalproject.dto.ExtraScheduleDTO;
import ru.jakev.hospitalproject.dto.PermanentScheduleDTO;
import ru.jakev.hospitalproject.entities.ExtraSchedule;
import ru.jakev.hospitalproject.entities.PermanentSchedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(source = "schedule.doctor.id", target = "doctorId")
    @Mapping(source = "schedule.hospital.id", target = "hospitalId")
    PermanentScheduleDTO permanentScheduleToPermanentScheduleDto(PermanentSchedule schedule);

    @InheritInverseConfiguration
    PermanentSchedule permanentScheduleDtoToPermanentSchedule(PermanentScheduleDTO permanentScheduleDTO);

    @Mapping(source = "schedule.doctor.id", target = "doctorId")
    @Mapping(source = "schedule.hospital.id", target = "hospitalId")
    ExtraScheduleDTO extraScheduleToExtraScheduleDto(ExtraSchedule schedule);

    ExtraSchedule extraScheduleDtoToExtraSchedule(ExtraScheduleDTO extraScheduleDTO);

}
