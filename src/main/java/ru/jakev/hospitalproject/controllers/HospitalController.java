package ru.jakev.hospitalproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jakev.hospitalproject.dto.HospitalDTO;
import ru.jakev.hospitalproject.services.HospitalService;

import java.util.List;

@RestController
@RequestMapping("hospitals")
@CrossOrigin
public class HospitalController {

    HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    private ResponseEntity<?> getAll(){
        List<HospitalDTO> hospitals = hospitalService.getAllHospitals();
        return new ResponseEntity<>(hospitals, HttpStatus.OK);
    }
}
