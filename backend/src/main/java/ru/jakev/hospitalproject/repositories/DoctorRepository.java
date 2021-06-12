package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.jakev.hospitalproject.entities.Doctor;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    @Query(value = "select " +
            "from doctor" +
            "         inner join doctor_hospitals dh on doctor.id = dh.doctors_id " +
            "where dh.hospitals_id = ?1", nativeQuery = true)
    List<Doctor> findAllByHospitalId(Integer id);
}
