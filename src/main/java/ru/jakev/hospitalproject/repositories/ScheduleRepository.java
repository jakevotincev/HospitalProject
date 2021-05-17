package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.jakev.hospitalproject.entities.Schedule;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findAllByDoctorId(Integer id);
    Optional<Schedule> findByDoctorIdAndDayOfWeek(Integer id, DayOfWeek day);
}
