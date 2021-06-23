package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.jakev.hospitalproject.entities.Doctor;
import ru.jakev.hospitalproject.entities.DoctorSpeciality;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    @Query(value = "select " +
            "from doctor" +
            "         inner join doctor_hospitals dh on doctor.id = dh.doctors_id " +
            "where dh.hospitals_id = ?1", nativeQuery = true)
    List<Doctor> findAllByHospitalId(Integer id);

    @Query(value = "select distinct speciality\n" +
            "from doctor\n" +
            "         inner join doctor_hospitals dh on doctor.id = dh.doctors_id " +
            "where dh.hospitals_id = ?1", nativeQuery = true)
    List<DoctorSpeciality> findAllSpecialitiesByHospitalId(Integer id);

    @Query(value = "select * from doctor " +
            "         inner join doctor_hospitals dh on doctor.id = dh.doctors_id " +
            "where dh.hospitals_id = ?2 and doctor.speciality=?1", nativeQuery = true)
    List<Doctor> findAllBySpecialityAndHospitalId(String speciality, Integer id);

    Optional<Doctor> findByNameAndSurnameAndMiddleName(String name, String surname, String middleName);
}
