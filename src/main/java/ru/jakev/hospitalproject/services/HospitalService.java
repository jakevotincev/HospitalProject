package ru.jakev.hospitalproject.services;

import ru.jakev.hospitalproject.dto.HospitalDTO;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public interface HospitalService {
    HospitalDTO saveDoctor(HospitalDTO hospitalDTO);
    List<HospitalDTO> getAllHospitals();
    HospitalDTO getHospitalById(Integer id) throws EntityNotFoundException;
}
