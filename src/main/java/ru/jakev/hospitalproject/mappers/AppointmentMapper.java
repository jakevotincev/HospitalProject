package ru.jakev.hospitalproject.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.jakev.hospitalproject.dto.AppointmentDTO;
import ru.jakev.hospitalproject.entities.Appointment;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "hospital", target = "hospitalDTO")
    AppointmentDTO appointmentToAppointmentDto(Appointment appointment);

    @InheritInverseConfiguration
    Appointment appointmentDtoToAppointment(AppointmentDTO appointmentDTO);
}
