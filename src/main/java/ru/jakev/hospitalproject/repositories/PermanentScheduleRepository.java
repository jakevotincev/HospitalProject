package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.jakev.hospitalproject.entities.PermanentSchedule;

import java.time.DayOfWeek;
import java.util.Optional;
import java.util.stream.Stream;

public interface PermanentScheduleRepository extends JpaRepository<PermanentSchedule, Integer> {
    @Query("select s from Schedule s where s.doctor.id=?1")
    Stream<PermanentSchedule> findAllByDoctorId(Integer id);
    Optional<PermanentSchedule> findByDoctorIdAndDayOfWeek(Integer id, DayOfWeek day);
}
