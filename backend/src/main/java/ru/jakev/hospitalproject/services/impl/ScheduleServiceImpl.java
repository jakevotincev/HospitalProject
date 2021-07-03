package ru.jakev.hospitalproject.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.jakev.hospitalproject.dto.DoctorDTO;
import ru.jakev.hospitalproject.dto.ExtraScheduleDTO;
import ru.jakev.hospitalproject.dto.HospitalDTO;
import ru.jakev.hospitalproject.dto.PermanentScheduleDTO;
import ru.jakev.hospitalproject.entities.ExtraSchedule;
import ru.jakev.hospitalproject.entities.PermanentSchedule;
import ru.jakev.hospitalproject.exceptions.EntityNotFoundException;
import ru.jakev.hospitalproject.exceptions.InvalidScheduleException;
import ru.jakev.hospitalproject.mappers.PeopleMapper;
import ru.jakev.hospitalproject.mappers.ScheduleMapper;
import ru.jakev.hospitalproject.repositories.ExtraScheduleRepository;
import ru.jakev.hospitalproject.repositories.PermanentScheduleRepository;
import ru.jakev.hospitalproject.services.DoctorService;
import ru.jakev.hospitalproject.services.HospitalService;
import ru.jakev.hospitalproject.services.ScheduleService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ScheduleServiceImpl.class);
    private final PermanentScheduleRepository permanentScheduleRepository;
    private final ExtraScheduleRepository extraScheduleRepository;
    private final DoctorService doctorService;
    private final ScheduleMapper scheduleMapper;
    private final PeopleMapper peopleMapper;
    private final HospitalService hospitalService;

    public ScheduleServiceImpl(PermanentScheduleRepository permanentScheduleRepository, ExtraScheduleRepository extraScheduleRepository,
                               DoctorService doctorService, ScheduleMapper scheduleMapper, PeopleMapper peopleMapper, HospitalService hospitalService) {
        this.permanentScheduleRepository = permanentScheduleRepository;
        this.extraScheduleRepository = extraScheduleRepository;
        this.doctorService = doctorService;
        this.scheduleMapper = scheduleMapper;
        this.peopleMapper = peopleMapper;
        this.hospitalService = hospitalService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermanentScheduleDTO> getSchedulesByDoctorIdAndHospitalId(Integer doctorId, Integer hospitalId) {
        List<PermanentScheduleDTO> permanentScheduleDTOList;
        try (Stream<PermanentSchedule> scheduleStream = permanentScheduleRepository.findAllByDoctorIdAAndHospitalId(doctorId, hospitalId)) {
            permanentScheduleDTOList = scheduleStream.map(scheduleMapper::permanentScheduleToPermanentScheduleDto).collect(Collectors.toList());
        }
        LOGGER.info("found " + permanentScheduleDTOList.size() + " schedules, Doctor.id = " + doctorId +
                " Hospital.Id = " + hospitalId);
        return permanentScheduleDTOList;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public PermanentScheduleDTO getScheduleByDoctorIdAndDayOfWeekAndHospitalId(Integer doctorId, DayOfWeek day, Integer hospitalId) {
        PermanentSchedule schedule = permanentScheduleRepository.findByDoctorIdAndDayOfWeekAndHospitalId(doctorId, day, hospitalId).orElseThrow(() ->
                new EntityNotFoundException("Schedule {doctor_id: " + doctorId + ", hospital_id: "
                        + hospitalId + ", day: " + day.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + "} not found"));
        LOGGER.info("found " + schedule);
        return scheduleMapper.permanentScheduleToPermanentScheduleDto(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public ExtraScheduleDTO getScheduleByDateAndHospitalIdAndDoctorId(LocalDate date, Integer hospitalId, Integer doctorId) {
        ExtraSchedule schedule = extraScheduleRepository.getByDateAndHospitalIdAndDoctorId(date, hospitalId, doctorId).orElse(null);
        if (schedule == null) LOGGER.info("Extra schedule {doctorId: " + doctorId + " hospital_id: "
                + hospitalId + ", date: " + date + "} not found");
        else
            LOGGER.info("found " + schedule + " Date = " + date.format(DateTimeFormatter.ISO_LOCAL_DATE)
                    + " Doctor.Id = " + doctorId + " Hospital.Id = " + hospitalId);
        return scheduleMapper.extraScheduleToExtraScheduleDto(schedule);
    }

    @Override
    public PermanentScheduleDTO savePermanentSchedule(PermanentScheduleDTO permanentScheduleDTO) throws InvalidScheduleException {
        if (permanentScheduleDTO.getDoctorId() == null || permanentScheduleDTO.getHospitalId() == null)
            throw new InvalidScheduleException(permanentScheduleDTO);
        DoctorDTO doctorDTO = doctorService.getDoctorById(permanentScheduleDTO.getDoctorId());
        HospitalDTO hospitalDTO = hospitalService.getHospitalById(permanentScheduleDTO.getHospitalId());
        if (!doctorDTO.getHospitals().contains(hospitalDTO)) throw new InvalidScheduleException(permanentScheduleDTO);
        PermanentSchedule schedule = scheduleMapper.permanentScheduleDtoToPermanentSchedule(permanentScheduleDTO);
        schedule.setDoctor(peopleMapper.doctorDtoToDoctor(doctorDTO));
        schedule = permanentScheduleRepository.save(schedule);
        LOGGER.info(schedule + " saved");
        return scheduleMapper.permanentScheduleToPermanentScheduleDto(schedule);
    }

    @Override
    public ExtraScheduleDTO saveExtraSchedule(ExtraScheduleDTO extraScheduleDTO) throws InvalidScheduleException {
        if (extraScheduleDTO.getDoctorId() == null) throw new InvalidScheduleException(extraScheduleDTO);
        DoctorDTO doctorDTO = doctorService.getDoctorById(extraScheduleDTO.getDoctorId());
        ExtraSchedule schedule = scheduleMapper.extraScheduleDtoToExtraSchedule(extraScheduleDTO);
        schedule.setDoctor(peopleMapper.doctorDtoToDoctor(doctorDTO));
        schedule = extraScheduleRepository.save(schedule);
        LOGGER.info(schedule + " saved");
        return scheduleMapper.extraScheduleToExtraScheduleDto(schedule);
    }
}
