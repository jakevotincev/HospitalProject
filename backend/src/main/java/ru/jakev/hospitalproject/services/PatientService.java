package ru.jakev.hospitalproject.services;

import ru.jakev.hospitalproject.dto.PatientDTO;

public interface PatientService {
    PatientDTO savePatient(PatientDTO patientDTO);
    PatientDTO getPatientByInitials(String name, String surname, String middleName);
}
