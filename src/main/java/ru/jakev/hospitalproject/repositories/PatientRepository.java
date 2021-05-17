package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jakev.hospitalproject.entities.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
