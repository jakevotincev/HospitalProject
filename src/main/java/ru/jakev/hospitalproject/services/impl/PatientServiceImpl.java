package ru.jakev.hospitalproject.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.jakev.hospitalproject.dto.PatientDTO;
import ru.jakev.hospitalproject.entities.Patient;
import ru.jakev.hospitalproject.mappers.PeopleMapper;
import ru.jakev.hospitalproject.repositories.PatientRepository;
import ru.jakev.hospitalproject.services.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PeopleMapper mapper;
    private final static Logger LOGGER = LoggerFactory.getLogger(PatientService.class);

    public PatientServiceImpl(PatientRepository patientRepository, PeopleMapper mapper) {
        this.patientRepository = patientRepository;
        this.mapper = mapper;
    }

    @Override
    public PatientDTO savePatient(PatientDTO patientDTO) {
        Patient patient = mapper.patientDtoToPatient(patientDTO);
        patient = patientRepository.save(patient);
        LOGGER.info(patient + " saved");
        return mapper.patientToPatientDto(patient);
    }
}
