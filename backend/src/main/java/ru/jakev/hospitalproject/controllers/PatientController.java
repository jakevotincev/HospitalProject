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

    @PostMapping
    public ResponseEntity<?> save(@RequestBody PatientDTO patient) {
        PatientDTO savedPatient = patientService.savePatient(patient);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> getByInitials(@RequestParam(name = "name") String name,
                                           @RequestParam(name = "surname") String surname,
                                           @RequestParam(name = "middleName") String middleName) {
        PatientDTO patientDTO = patientService.getPatientByInitials(name, surname, middleName);
        return new ResponseEntity<>(patientDTO, HttpStatus.OK);
    }
}
