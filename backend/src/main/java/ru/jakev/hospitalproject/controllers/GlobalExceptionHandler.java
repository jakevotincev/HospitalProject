package ru.jakev.hospitalproject.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.jakev.hospitalproject.exceptions.EntityNotFoundException;
import ru.jakev.hospitalproject.exceptions.InvalidAppointmentException;
import ru.jakev.hospitalproject.exceptions.InvalidScheduleException;
import ru.jakev.hospitalproject.services.impl.HospitalServiceImpl;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(HospitalServiceImpl.class);

    @ExceptionHandler({EntityNotFoundException.class, InvalidScheduleException.class})
    public ResponseEntity<String> handleException(Exception exception) {
        if (exception instanceof EntityNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            LOGGER.info(exception.getMessage());
            return handleEntityNotFoundException((EntityNotFoundException) exception, status);
        } else if (exception instanceof InvalidScheduleException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            LOGGER.info(exception.getMessage());
            return handleInvalidScheduleException((InvalidScheduleException) exception, status);
        } else if (exception instanceof InvalidAppointmentException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            LOGGER.info(exception.getMessage());
            return handleInvalidAppointmentException(status);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOGGER.error(exception.getMessage());
            return new ResponseEntity<>("Server error", status);
        }
    }

    protected ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exception, HttpStatus status) {
        return new ResponseEntity<>(exception.getMessage(), status);
    }

    protected ResponseEntity<String> handleInvalidScheduleException(InvalidScheduleException exception, HttpStatus status) {
        String message = "Invalid doctor id: " + exception.getScheduleDTO().getDoctorId() + " or invalid hospital id: "
                + exception.getScheduleDTO().getHospitalId();
        return new ResponseEntity<>(message, status);
    }

    protected ResponseEntity<String> handleInvalidAppointmentException( HttpStatus status) {
        String message = "Invalid appointment data";
        return new ResponseEntity<>(message, status);
    }


}
