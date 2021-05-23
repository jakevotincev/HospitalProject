package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.jakev.hospitalproject.entities.Schedule;

import java.time.DayOfWeek;
import java.util.Optional;
import java.util.stream.Stream;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("select s from Schedule s where s.doctor.id=?1")
    Stream<Schedule> findAllByDoctorId(Integer id);
    Optional<Schedule> findByDoctorIdAndDayOfWeek(Integer id, DayOfWeek day);
}
