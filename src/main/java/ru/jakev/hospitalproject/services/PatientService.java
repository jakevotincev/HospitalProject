package ru.jakev.hospitalproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jakev.hospitalproject.dto.PatientDTO;
import ru.jakev.hospitalproject.entities.Patient;
import ru.jakev.hospitalproject.mappers.PeopleMapper;
import ru.jakev.hospitalproject.repositories.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PeopleMapper mapper;

    @Autowired
    public PatientService(PatientRepository patientRepository, PeopleMapper mapper) {
        this.patientRepository = patientRepository;
        this.mapper = mapper;
    }

    public PatientDTO savePatient(PatientDTO patientDTO){
        Patient patient = mapper.patientDtoToPatient(patientDTO);
        return mapper.patientToPatientDto(patientRepository.save(patient));
    }
}
