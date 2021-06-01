package ru.jakev.hospitalproject.mappers;

import org.mapstruct.Mapper;
import ru.jakev.hospitalproject.dto.DoctorDTO;
import ru.jakev.hospitalproject.dto.PatientDTO;
import ru.jakev.hospitalproject.entities.Doctor;
import ru.jakev.hospitalproject.entities.Patient;

@Mapper(componentModel = "spring", uses = {HospitalMapper.class})
public interface PeopleMapper {

    PatientDTO patientToPatientDto(Patient patient);

    Patient patientDtoToPatient(PatientDTO patientDTO);

    DoctorDTO doctorToDoctorDto(Doctor doctor);

    Doctor doctorDtoToDoctor(DoctorDTO doctorDTO);
}
