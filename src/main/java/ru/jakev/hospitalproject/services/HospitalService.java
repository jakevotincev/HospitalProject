package ru.jakev.hospitalproject.services;

import ru.jakev.hospitalproject.dto.HospitalDTO;

import java.util.List;

public interface HospitalService {
    HospitalDTO saveDoctor(HospitalDTO hospitalDTO);
    List<HospitalDTO> getAllHospitals();
}
