package ru.jakev.hospitalproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jakev.hospitalproject.dto.DoctorDTO;
import ru.jakev.hospitalproject.entities.DoctorSpeciality;
import ru.jakev.hospitalproject.services.DoctorService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

//todo: refactor all unused methods (at the end)
@RestController
@RequestMapping
@CrossOrigin
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody DoctorDTO doctor) {
        DoctorDTO savedDoctor = doctorService.saveDoctor(doctor);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

//    @GetMapping("doctors")
//    public ResponseEntity<?> getAll() {
//        List<DoctorDTO> doctors = doctorService.getAllDoctors();
//        return new ResponseEntity<>(doctors, HttpStatus.OK);
//    }

    @GetMapping("hospitals/{id}/doctors/specialities")
    public ResponseEntity<?> getSpecialitiesByHospitalId(@PathVariable("id") Integer id) {
        List<DoctorSpeciality> specialities = doctorService.getSpecialitiesByHospitalId(id);
        return new ResponseEntity<>(specialities, HttpStatus.OK);
    }

    @GetMapping("hospitals/{h_id}/doctors/{speciality}")
    public ResponseEntity<?> getDoctorsByHospitalAndSpeciality(@PathVariable("h_id") Integer hospitalId,
                                                           @PathVariable("speciality") String speciality) {
        List<DoctorDTO> doctors = doctorService.getAllBySpecialityAndHospitalId(speciality, hospitalId);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("doctors")
    public ResponseEntity<?> getByInitials(@RequestParam(name = "name") String name,
                         @RequestParam(name = "surname") String surname,
                         @RequestParam(name = "middleName") String middleName){
        DoctorDTO doctorDTO;
        try {
            doctorDTO = doctorService.getByInitials(name, surname, middleName);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
    }
}
