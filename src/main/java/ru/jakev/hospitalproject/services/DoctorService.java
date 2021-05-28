package ru.jakev.hospitalproject.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.jakev.hospitalproject.dto.DoctorDTO;
import ru.jakev.hospitalproject.entities.Doctor;
import ru.jakev.hospitalproject.mappers.PeopleMapper;
import ru.jakev.hospitalproject.repositories.DoctorRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
//todo: if getAllDoctors list is empty do I need to throw exception
public class DoctorService {

    private final Logger LOGGER = LoggerFactory.getLogger(DoctorService.class);
    private final DoctorRepository doctorRepository;
    private final PeopleMapper peopleMapper;

    public DoctorService(DoctorRepository doctorRepository, PeopleMapper peopleMapper) {
        this.doctorRepository = doctorRepository;
        this.peopleMapper = peopleMapper;
    }

    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctorList = doctorRepository.findAll();
        LOGGER.info("found " + doctorList.size() + " doctors");
        return doctorList.stream().map(peopleMapper::doctorToDoctorDto).collect(Collectors.toList());
    }

    public DoctorDTO saveDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = peopleMapper.doctorDtoToDoctor(doctorDTO);
        doctor = doctorRepository.save(doctor);
        LOGGER.info(doctor + " saved");
        return peopleMapper.doctorToDoctorDto(doctor);
    }

    //todo: add test
    public DoctorDTO getDoctorById(Integer id){
        Doctor doctor = doctorRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Not found entity Doctor(id = " + id + ")"));
        LOGGER.info("found " + doctor);
        return peopleMapper.doctorToDoctorDto(doctor);
    }
}
