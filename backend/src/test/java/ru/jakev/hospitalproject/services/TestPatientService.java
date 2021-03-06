package ru.jakev.hospitalproject.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jakev.hospitalproject.dto.PatientDTO;
import ru.jakev.hospitalproject.entities.Patient;
import ru.jakev.hospitalproject.mappers.PeopleMapper;
import ru.jakev.hospitalproject.repositories.PatientRepository;
import ru.jakev.hospitalproject.services.impl.PatientServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TestPatientService {

    PatientService patientService;
    @Mock PatientRepository patientRepository;

    @BeforeEach
    void init(){
        PeopleMapper mapper = Mappers.getMapper(PeopleMapper.class);
        patientService = new PatientServiceImpl(patientRepository, mapper);
    }

    @Test
    void testSave(){
        PatientDTO patient = new PatientDTO(1, "surname", "name", "middlename", 20);
        Mockito.when(patientRepository.save(Mockito.any(Patient.class))).then(AdditionalAnswers.returnsFirstArg());

        PatientDTO newPatient = patientService.savePatient(patient);
        assertEquals(patient, newPatient);
    }
}
