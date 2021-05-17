package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jakev.hospitalproject.entities.Appointment;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findAllByDoctorId(Integer id);
    List<Appointment> findAllByPatientId(Integer id);
    List<Appointment> findAllByDoctorIdAndDateBetween(Integer id, LocalDateTime from, LocalDateTime to);
}
