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
import ru.jakev.hospitalproject.dto.DoctorDTO;
import ru.jakev.hospitalproject.entities.Doctor;
import ru.jakev.hospitalproject.entities.DoctorSpeciality;
import ru.jakev.hospitalproject.mappers.PeopleMapper;
import ru.jakev.hospitalproject.repositories.DoctorRepository;

import java.util.ArrayList;
import java.util.List;

//todo:add testSave with null
@ExtendWith(MockitoExtension.class)
public class TestDoctorService {

    DoctorService doctorService;
    @Mock DoctorRepository doctorRepository;

    @BeforeEach
    void init(){
        PeopleMapper peopleMapper = Mappers.getMapper(PeopleMapper.class);
        doctorService = new DoctorService(doctorRepository, peopleMapper);
    }

    @Test
    void testGetAllDoctors(){
        List<Doctor> list = new ArrayList<>();
        list.add(new Doctor(1, "surname", "name", "middle_name", DoctorSpeciality.DENTIST, 10));
        list.add(new Doctor(2, "surname", "name", "middle_name", DoctorSpeciality.DENTIST, 10));
        list.add(new Doctor(3, "surname", "name", "middle_name", DoctorSpeciality.DENTIST, 10));

        Mockito.when(doctorRepository.findAll()).thenReturn(list);

        List<DoctorDTO> doctorList = doctorService.getAllDoctors();

        assertEquals(3, doctorList.size());
    }

    @Test
    void testGetAllDoctorsWithEmptyList(){
        Mockito.when(doctorRepository.findAll()).thenReturn(new ArrayList<>());
        List<DoctorDTO> doctorList = doctorService.getAllDoctors();
        assertTrue(doctorList.isEmpty());
    }

    @Test
    void testSave(){
        DoctorDTO doctor = new DoctorDTO(1, "surname", "name", "middle_name", DoctorSpeciality.DENTIST, 10);
        Mockito.when(doctorRepository.save(Mockito.any(Doctor.class))).then(AdditionalAnswers.returnsFirstArg());

        DoctorDTO newDoctor = doctorService.saveDoctor(doctor);
        assertEquals(doctor, newDoctor);
    }

}
