package ru.jakev.hospitalproject.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.jakev.hospitalproject.dto.AppointmentDTO;
import ru.jakev.hospitalproject.entities.Appointment;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {


    AppointmentDTO appointmentToAppointmentDto(Appointment appointment);


    Appointment appointmentDtoToAppointment(AppointmentDTO appointmentDTO);
}
