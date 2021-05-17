package ru.jakev.hospitalproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jakev.hospitalproject.entities.Patient;
import ru.jakev.hospitalproject.repositories.PatientRepository;

@Service
//todo: do I need use patient or patientDto as argument in savePatient
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient savePatient(Patient patient){
        return patientRepository.save(patient);
    }
}
