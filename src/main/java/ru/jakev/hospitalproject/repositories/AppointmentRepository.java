package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jakev.hospitalproject.entities.Appointment;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Optional<Appointment> findAllByDoctorId(Integer id);
    Optional<Appointment> findAllByPatientId(Integer id);
    Optional<Appointment> findAllByDoctorIdAndDateBetween(Integer id, LocalDateTime from, LocalDateTime to);
}
