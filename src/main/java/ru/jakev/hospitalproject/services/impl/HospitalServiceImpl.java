package ru.jakev.hospitalproject.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.jakev.hospitalproject.dto.HospitalDTO;
import ru.jakev.hospitalproject.entities.Hospital;
import ru.jakev.hospitalproject.mappers.HospitalMapper;
import ru.jakev.hospitalproject.repositories.HospitalRepository;
import ru.jakev.hospitalproject.services.HospitalService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final HospitalMapper hospitalMapper;
    private final static Logger LOGGER = LoggerFactory.getLogger(HospitalServiceImpl.class);

    public HospitalServiceImpl(HospitalRepository hospitalRepository, HospitalMapper hospitalMapper) {
        this.hospitalRepository = hospitalRepository;
        this.hospitalMapper = hospitalMapper;
    }

    @Override
    public HospitalDTO saveDoctor(HospitalDTO hospitalDTO) {
        Hospital hospital = hospitalMapper.hospitalDTOToHospital(hospitalDTO);
        hospital = hospitalRepository.save(hospital);
        LOGGER.info(hospital + " saved");
        return hospitalMapper.hospitalToHospitalDTO(hospital);
    }

    @Override
    public List<HospitalDTO> getAllHospitals() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        LOGGER.info("found " + hospitals.size() + " hospitals");
        return hospitals.stream().map(hospitalMapper::hospitalToHospitalDTO).collect(Collectors.toList());
    }

    @Override
    public HospitalDTO getHospitalById(Integer id) throws EntityNotFoundException {
        Hospital hospital = hospitalRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("hospital with id = " + id + "not found"));
        return hospitalMapper.hospitalToHospitalDTO(hospital);
    }
}
