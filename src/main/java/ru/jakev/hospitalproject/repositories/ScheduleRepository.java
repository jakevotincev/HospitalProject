package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jakev.hospitalproject.entities.Schedule;

import java.time.DayOfWeek;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Optional<Schedule> findAllByDoctorId(Integer id);
    Optional<Schedule> findAllByDoctorIdAndDayOfWeek(Integer id, DayOfWeek day);
}
