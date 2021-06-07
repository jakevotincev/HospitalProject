package ru.jakev.hospitalproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jakev.hospitalproject.dto.AppointmentDTO;
import ru.jakev.hospitalproject.services.AppointmentService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class AppointmentController {


    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("appointments")
    public ResponseEntity<?> create(@RequestBody AppointmentDTO appointment) {
        AppointmentDTO appointmentDTO;
        try {
            appointmentDTO = appointmentService.saveAppointment(appointment);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

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

}
