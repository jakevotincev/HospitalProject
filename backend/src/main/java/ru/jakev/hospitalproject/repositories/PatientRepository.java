package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jakev.hospitalproject.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
