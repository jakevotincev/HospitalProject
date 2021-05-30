package ru.jakev.hospitalproject.services;

import ru.jakev.hospitalproject.dto.DoctorDTO;

import java.util.List;


public interface DoctorService {

    List<DoctorDTO> getAllDoctors();

    DoctorDTO saveDoctor(DoctorDTO doctorDTO);

    DoctorDTO getDoctorById(Integer id);
}
