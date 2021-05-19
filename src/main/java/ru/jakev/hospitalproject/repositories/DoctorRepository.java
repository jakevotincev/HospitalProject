package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jakev.hospitalproject.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
}
