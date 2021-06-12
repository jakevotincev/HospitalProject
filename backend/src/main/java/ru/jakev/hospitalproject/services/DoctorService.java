package ru.jakev.hospitalproject.services;

import ru.jakev.hospitalproject.dto.DoctorDTO;

import java.util.List;

//todo: add find by name
//todo: add get all by speciality?
public interface DoctorService {

    List<DoctorDTO> getAllDoctors();

    DoctorDTO saveDoctor(DoctorDTO doctorDTO);

    DoctorDTO getDoctorById(Integer id);

    List<DoctorDTO> getAllByHospitalId(Integer id);
}
