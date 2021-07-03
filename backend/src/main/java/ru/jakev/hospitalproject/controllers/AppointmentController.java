package ru.jakev.hospitalproject.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jakev.hospitalproject.dto.AppointmentDTO;
import ru.jakev.hospitalproject.exceptions.InvalidAppointmentException;
import ru.jakev.hospitalproject.services.AppointmentService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin
public class AppointmentController {


    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("appointments")
    public ResponseEntity<?> save(@RequestBody AppointmentDTO appointment) throws InvalidAppointmentException {
        AppointmentDTO appointmentDTO = appointmentService.saveAppointment(appointment);
        return new ResponseEntity<>(appointmentDTO, HttpStatus.CREATED);
    }

    @GetMapping("hospitals/{h_id}/doctors/{d_id}/appointments")
    public ResponseEntity<?> getByDoctorId(@PathVariable("h_id") Integer h_id, @PathVariable("d_id") Integer d_id) {
        List<AppointmentDTO> appointmentDTOList = appointmentService.getAppointmentsByDoctorIdAndHospitalId(d_id, h_id);
        return new ResponseEntity<>(appointmentDTOList, HttpStatus.OK);
    }

    @GetMapping("patients/{p_id}/appointments")
    public ResponseEntity<?> getByPatientId(@PathVariable("p_id") Integer p_id) {
        List<AppointmentDTO> appointmentDTOList = appointmentService.getAppointmentsByPatientId(p_id);
        return new ResponseEntity<>(appointmentDTOList, HttpStatus.OK);
    }


    @GetMapping("hospitals/{h_id}/doctors/{d_id}/appointments/{date}")
    public ResponseEntity<?> getScheduleForDay(@PathVariable("h_id") Integer h_id, @PathVariable("d_id") Integer d_id,
                                               @PathVariable("date") @DateTimeFormat(pattern = "dd_MM_yyyy") LocalDate date) {
        Map<LocalTime, Boolean> schedule = appointmentService.getScheduleByDoctorIdAndDateAndHospitalId(d_id, date, h_id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @GetMapping("doctors/{d_id}/appointments/{from}/{to}")
    public ResponseEntity<?> getAppointmentsBetween(@PathVariable("from")
                                                    @DateTimeFormat(pattern = "dd_MM_yyyy HH:mm:ss") LocalDateTime from,
                                                    @PathVariable("to") @DateTimeFormat(pattern = "dd_MM_yyyy HH:mm:ss")
                                                            LocalDateTime to, @PathVariable("d_id") Integer d_id) {
        List<AppointmentDTO> appointmentDTOList = appointmentService.getAppointmentsByDoctorIdAndDateBetween(
                d_id, from, to);
        return new ResponseEntity<>(appointmentDTOList, HttpStatus.OK);
    }
}
