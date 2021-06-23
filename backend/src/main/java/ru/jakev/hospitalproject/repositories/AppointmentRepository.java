package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jakev.hospitalproject.entities.Appointment;

import java.time.LocalDateTime;
import java.util.stream.Stream;


public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Stream<Appointment> findAllByDoctorIdAndHospitalId(Integer doctorId, Integer hospitalId);
    Stream<Appointment> findAllByPatientId(Integer patientId);
    Stream<Appointment> findAllByDoctorIdAndHospitalIdAndDateBetween(Integer doctorId, Integer hospitalId, LocalDateTime from, LocalDateTime to);
    Stream<Appointment> findAllByDoctorIdAndDateBetween(Integer doctorId, LocalDateTime from, LocalDateTime to);
}
