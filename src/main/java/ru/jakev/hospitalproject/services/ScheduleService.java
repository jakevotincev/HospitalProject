package ru.jakev.hospitalproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jakev.hospitalproject.entities.Schedule;
import ru.jakev.hospitalproject.repositories.ScheduleRepository;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

//todo: write custom entity not found exception???
//todo: maybe write save method
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getSchedulesByDoctorId(Integer id) {
        return scheduleRepository.findAllByDoctorId(id);
    }

    public Schedule getScheduleByDoctorIdAndDayOfWeek(Integer id, DayOfWeek day) throws EntityNotFoundException{
        return scheduleRepository.findByDoctorIdAndDayOfWeek(id, day)
                .orElseThrow(() -> new EntityNotFoundException("Entity doctor_id: " + id + ", day: "
                        + day.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " not found"));
    }

}
