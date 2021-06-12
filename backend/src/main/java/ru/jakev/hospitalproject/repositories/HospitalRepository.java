package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jakev.hospitalproject.entities.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

}
