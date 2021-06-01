package ru.jakev.hospitalproject.mappers;

import org.mapstruct.Mapper;
import ru.jakev.hospitalproject.dto.AppointmentDTO;
import ru.jakev.hospitalproject.entities.Appointment;

@Mapper(componentModel = "spring", uses = {HospitalMapper.class})
public interface AppointmentMapper {

    AppointmentDTO appointmentToAppointmentDto(Appointment appointment);

    Appointment appointmentDtoToAppointment(AppointmentDTO appointmentDTO);
}
