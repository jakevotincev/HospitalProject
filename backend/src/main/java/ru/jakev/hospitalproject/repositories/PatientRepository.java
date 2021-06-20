package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jakev.hospitalproject.entities.Patient;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> findByNameAndSurnameAndMiddleName(String name, String surname, String middleName);
}
