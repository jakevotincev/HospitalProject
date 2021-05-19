package ru.jakev.hospitalproject.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jakev.hospitalproject.entities.Patient;
import ru.jakev.hospitalproject.repositories.PatientRepository;

//todo: add testSave with null
@ExtendWith(MockitoExtension.class)
public class TestPatientService {

    PatientService patientService;
    @Mock PatientRepository patientRepository;

    @BeforeEach
    void init(){
        patientService = new PatientService(patientRepository);
    }

    @Test
    void testSave(){
        Patient patient = new Patient(1, "surname", "name", "middlename", 20);
        Mockito.when(patientRepository.save(Mockito.any(Patient.class))).then(AdditionalAnswers.returnsFirstArg());

        Patient newPatient = patientService.savePatient(patient);
        assertEquals(patient, newPatient);
    }
}
