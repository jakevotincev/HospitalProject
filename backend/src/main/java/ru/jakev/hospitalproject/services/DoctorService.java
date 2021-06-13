package ru.jakev.hospitalproject.services;

import ru.jakev.hospitalproject.dto.DoctorDTO;
import ru.jakev.hospitalproject.entities.DoctorSpeciality;

import java.util.List;

public interface DoctorService {

    List<DoctorDTO> getAllDoctors();

    DoctorDTO saveDoctor(DoctorDTO doctorDTO);

    DoctorDTO getDoctorById(Integer id);

    List<DoctorDTO> getAllByHospitalId(Integer id);

    List<DoctorSpeciality> getSpecialitiesByHospitalId(Integer hospitalId);

    List<DoctorDTO> getAllBySpecialityAndHospitalId(String speciality, Integer hospitalId);
}
