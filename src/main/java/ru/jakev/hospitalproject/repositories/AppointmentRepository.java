package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jakev.hospitalproject.entities.Appointment;

import java.time.LocalDateTime;
import java.util.stream.Stream;


public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Stream<Appointment> findAllByDoctorId(Integer id);
    Stream<Appointment> findAllByPatientId(Integer id);
    Stream<Appointment> findAllByDoctorIdAndDateBetween(Integer id, LocalDateTime from, LocalDateTime to);
}
