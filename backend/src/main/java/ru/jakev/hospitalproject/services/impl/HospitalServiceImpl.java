package ru.jakev.hospitalproject.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.jakev.hospitalproject.dto.HospitalDTO;
import ru.jakev.hospitalproject.entities.Hospital;
import ru.jakev.hospitalproject.exceptions.EntityNotFoundException;
import ru.jakev.hospitalproject.mappers.HospitalMapper;
import ru.jakev.hospitalproject.repositories.HospitalRepository;
import ru.jakev.hospitalproject.services.HospitalService;

import java.util.List;
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
    public HospitalDTO saveHospital(HospitalDTO hospitalDTO) {
        Hospital hospital = hospitalMapper.hospitalDTOToHospital(hospitalDTO);
        hospital = hospitalRepository.save(hospital);
        LOGGER.info(hospital + " saved");
        return hospitalMapper.hospitalToHospitalDTO(hospital);
    }

    @Override
    @Cacheable("hospital")
    public List<HospitalDTO> getAllHospitals() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        LOGGER.info("found " + hospitals.size() + " hospitals");
        return hospitals.stream().map(hospitalMapper::hospitalToHospitalDTO).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "hospital", key = "#id")
    public HospitalDTO getHospitalById(Integer id){
        Hospital hospital = hospitalRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Hospital with id = " + id + "not found"));
        return hospitalMapper.hospitalToHospitalDTO(hospital);
    }
}
