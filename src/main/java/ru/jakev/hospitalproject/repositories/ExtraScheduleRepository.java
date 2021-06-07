package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jakev.hospitalproject.entities.ExtraSchedule;

import java.time.LocalDate;
import java.util.Optional;

public interface ExtraScheduleRepository extends JpaRepository<ExtraSchedule, Integer> {
    Optional<ExtraSchedule> getByDateAndHospitalIdAndDoctorId(LocalDate date, Integer hospital_id, Integer doctor_id);
}
