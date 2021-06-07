package ru.jakev.hospitalproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jakev.hospitalproject.dto.PermanentScheduleDTO;
import ru.jakev.hospitalproject.services.ScheduleService;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("schedules")
    public ResponseEntity<?> save(@RequestBody PermanentScheduleDTO schedule) {
        PermanentScheduleDTO savedSchedule;
        try {
            savedSchedule = scheduleService.savePermanentSchedule(schedule);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid doctor id: " + schedule.getDoctorId(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedSchedule, HttpStatus.CREATED);
    }

    @GetMapping("hospitals/{h_id}/doctors/{d_id}/schedules")
    public ResponseEntity<?> getByHospitalIdAndDoctorId(@PathVariable("h_id") Integer hospitalId, @PathVariable("d_id") Integer doctorId) {
        List<PermanentScheduleDTO> scheduleList = scheduleService.getSchedulesByDoctorIdAndHospitalId(doctorId, hospitalId);
        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }

//    @GetMapping("/hospitals/{h_id}/doctors/{d_id}/schedules/{day}")
//    public ResponseEntity<?> getByDoctorIdAndDayOfWeek(@PathVariable("id") Integer id, @PathVariable("day") DayOfWeek day) {
//        PermanentScheduleDTO schedule;
//        try {
//            schedule = scheduleService.getScheduleByDoctorIdAndDayOfWeek(id, day);
//        } catch (EntityNotFoundException e) {
//            return new ResponseEntity<>("Not found schedule Doctor.id: " + id + " day: " + day, HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(schedule, HttpStatus.OK);
//    }
}
