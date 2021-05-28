package ru.jakev.hospitalproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jakev.hospitalproject.dto.ScheduleDTO;
import ru.jakev.hospitalproject.services.ScheduleService;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
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
    private ResponseEntity<?> create(@RequestBody ScheduleDTO schedule) {
        ScheduleDTO savedSchedule;
        try {
            savedSchedule = scheduleService.saveSchedule(schedule);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid doctor id: " + schedule.getDoctorId(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedSchedule, HttpStatus.CREATED);
    }

    @GetMapping("doctors/{id}/schedules")
    public ResponseEntity<?> getByDoctorId(@PathVariable("id") Integer id) {
        List<ScheduleDTO> scheduleList = scheduleService.getSchedulesByDoctorId(id);
        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }

    @GetMapping("doctors/{id}/schedules/{day}")
    public ResponseEntity<?> getByDoctorIdAndDayOfWeek(@PathVariable("id") Integer id, @PathVariable("day") DayOfWeek day) {
        ScheduleDTO schedule;
        try {
            schedule = scheduleService.getScheduleByDoctorIdAndDayOfWeek(id, day);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Not found schedule Doctor.id: " + id + " day: " + day, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }
}
