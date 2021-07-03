package ru.jakev.hospitalproject.exceptions;

import ru.jakev.hospitalproject.dto.AppointmentDTO;

public class InvalidAppointmentException extends Exception{
    public InvalidAppointmentException(Throwable cause, AppointmentDTO appointmentDTO) {
        super("Invalid appointment dto: " + appointmentDTO, cause);
    }
}
