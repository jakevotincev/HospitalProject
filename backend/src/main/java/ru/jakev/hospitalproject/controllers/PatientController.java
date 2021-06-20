package ru.jakev.hospitalproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jakev.hospitalproject.dto.PatientDTO;
import ru.jakev.hospitalproject.services.PatientService;

import javax.persistence.EntityNotFoundException;

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
    public ResponseEntity<?> save(@RequestBody PatientDTO patient) {
        PatientDTO savedPatient = patientService.savePatient(patient);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> getByInitials(@RequestParam(name = "name") String name,
                                           @RequestParam(name = "surname") String surname,
                                           @RequestParam(name = "middleName") String middleName) {
        PatientDTO patientDTO;
        try {
            patientDTO = patientService.getPatientByInitials(name, surname, middleName);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(patientDTO, HttpStatus.OK);
    }
}
