package ru.jakev.hospitalproject.mappers;

import org.mapstruct.Mapper;
import ru.jakev.hospitalproject.dto.HospitalDTO;
import ru.jakev.hospitalproject.entities.Hospital;

@Mapper(componentModel = "spring")
public interface HospitalMapper {

    HospitalDTO hospitalToHospitalDTO(Hospital hospital);

    Hospital hospitalDTOToHospital(HospitalDTO hospitalDTO);
}
