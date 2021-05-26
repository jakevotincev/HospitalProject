package ru.jakev.hospitalproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jakev.hospitalproject.dto.PatientDTO;
import ru.jakev.hospitalproject.services.PatientService;

@RestController
@RequestMapping("patients")
@CrossOrigin
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    //todo: how to catch exception if field equals null
    @PostMapping
    private ResponseEntity<?> create(@RequestBody PatientDTO patient) {
        PatientDTO savedPatient = patientService.savePatient(patient);
        return new ResponseEntity<>(savedPatient, HttpStatus.OK);
    }
}
