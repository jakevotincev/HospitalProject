package ru.jakev.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.jakev.hospitalproject.entities.Doctor;
import ru.jakev.hospitalproject.entities.DoctorSpeciality;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    @Query("from Doctor as doctor" +
            "       inner join doctor.hospitals as hospital " +
            "where hospital.id = :id")
    List<Doctor> findAllByHospitalId(Integer id);

    @Query("select distinct doctor.speciality " +
            "from Doctor as doctor" +
            "         inner join doctor.hospitals as hospital " +
            "where hospital.id = :id")
    List<DoctorSpeciality> findAllSpecialitiesByHospitalId(Integer id);

    @Query("from Doctor as doctor" +
            "         inner join doctor.hospitals as hospital " +
            "where hospital.id = :id and doctor.speciality=:speciality")
    List<Doctor> findAllBySpecialityAndHospitalId(DoctorSpeciality speciality, Integer id);

    Optional<Doctor> findByNameAndSurnameAndMiddleName(String name, String surname, String middleName);
}
