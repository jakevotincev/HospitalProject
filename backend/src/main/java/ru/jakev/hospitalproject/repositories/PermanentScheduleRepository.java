package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.jakev.hospitalproject.entities.PermanentSchedule;

import java.time.DayOfWeek;
import java.util.Optional;
import java.util.stream.Stream;

public interface PermanentScheduleRepository extends JpaRepository<PermanentSchedule, Integer> {
    @Query("from PermanentSchedule as s where s.doctor.id=:doctor_id and s.hospital.id=:hospital_id")
    Stream<PermanentSchedule> findAllByDoctorIdAAndHospitalId(Integer doctor_id, Integer hospital_id);
    Optional<PermanentSchedule> findByDoctorIdAndDayOfWeekAndHospitalId(Integer doctorId, DayOfWeek day, Integer hospitalId);
}
