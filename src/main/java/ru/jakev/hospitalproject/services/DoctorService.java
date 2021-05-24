package ru.jakev.hospitalproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jakev.hospitalproject.dto.DoctorDTO;
import ru.jakev.hospitalproject.entities.Doctor;
import ru.jakev.hospitalproject.mappers.PeopleMapper;
import ru.jakev.hospitalproject.repositories.DoctorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
//todo: if getAllDoctors list is empty do I need to throw exception
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PeopleMapper peopleMapper;

    public DoctorService(DoctorRepository doctorRepository, PeopleMapper peopleMapper) {
        this.doctorRepository = doctorRepository;
        this.peopleMapper = peopleMapper;
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream().map(peopleMapper::doctorToDoctorDto).collect(Collectors.toList());
    }

    public DoctorDTO saveDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = peopleMapper.doctorDtoToDoctor(doctorDTO);
        return peopleMapper.doctorToDoctorDto(doctorRepository.save(doctor));
    }
}
