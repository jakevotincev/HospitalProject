package ru.jakev.hospitalproject.services;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.jakev.hospitalproject.dto.PermanentScheduleDTO;
import ru.jakev.hospitalproject.entities.*;
import ru.jakev.hospitalproject.mappers.HospitalMapper;
import ru.jakev.hospitalproject.mappers.PeopleMapper;
import ru.jakev.hospitalproject.mappers.ScheduleMapper;
import ru.jakev.hospitalproject.repositories.ExtraScheduleRepository;
import ru.jakev.hospitalproject.repositories.PermanentScheduleRepository;
import ru.jakev.hospitalproject.services.impl.ScheduleServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TestScheduleService {

    ScheduleService scheduleService;
    @Mock
    PermanentScheduleRepository permanentScheduleRepository;
    @Mock
    ExtraScheduleRepository extraScheduleRepository;
    @Mock
    DoctorService doctorService;
    @Mock
    HospitalService hospitalService;
    List<Schedule> scheduleList = new ArrayList<>();
    ScheduleMapper scheduleMapper;
    PeopleMapper peopleMapper;
    HospitalMapper hospitalMapper;
    private final static Logger LOGGER = LoggerFactory.getLogger(TestScheduleService.class);


    @BeforeEach
    void init() {
        scheduleMapper = Mappers.getMapper(ScheduleMapper.class);
        peopleMapper = Mappers.getMapper(PeopleMapper.class);
        hospitalMapper = Mappers.getMapper(HospitalMapper.class);
        scheduleService = new ScheduleServiceImpl(permanentScheduleRepository, extraScheduleRepository, doctorService, scheduleMapper, peopleMapper, hospitalService);
        Doctor doctor = new Doctor(1, "surname", "name", "middle_name",
                DoctorSpeciality.DENTIST, null);
        Doctor doctor2 = new Doctor(2, "surname", "name", "middle_name",
                DoctorSpeciality.DENTIST, null);
        Hospital hospital = new Hospital(1, "hospital", "street", Set.of(doctor, doctor2));
        doctor.setHospitals(Collections.singleton(hospital));
        doctor2.setHospitals(Collections.singleton(hospital));
        scheduleList.add(new PermanentSchedule(1, doctor, LocalTime.of(9, 0),
                LocalTime.of(18, 0), Duration.ofHours(1), hospital, DayOfWeek.MONDAY));
        scheduleList.add(new PermanentSchedule(2, doctor, LocalTime.of(9, 0),
                LocalTime.of(18, 0), Duration.ofHours(1), hospital, DayOfWeek.TUESDAY));
        scheduleList.add(new PermanentSchedule(3, doctor2, LocalTime.of(9, 0),
                LocalTime.of(18, 0), Duration.ofHours(1), hospital, DayOfWeek.MONDAY));
    }

    @SneakyThrows
    @Test
    void testGetSchedulesByDoctorId() {
        Mockito.when(permanentScheduleRepository.findAllByDoctorIdAAndHospitalId(Mockito.anyInt(), Mockito.anyInt())).thenAnswer(invocationOnMock -> {
            List<Schedule> schedules;
            switch ((Integer) invocationOnMock.getArgument(0)) {
                case 1:
                    schedules = new ArrayList<>(Arrays.asList(scheduleList.get(0), scheduleList.get(1)));
                    break;
                case 2:
                    schedules = new ArrayList<>(Collections.singletonList(scheduleList.get(2)));
                    break;
                default:
                    schedules = new ArrayList<>();
            }
            return schedules.stream();
        });

        List<PermanentScheduleDTO> foundSchedules = scheduleService.getSchedulesByDoctorIdAndHospitalId(1, 1);
        assertEquals(2, foundSchedules.size());

        foundSchedules = scheduleService.getSchedulesByDoctorIdAndHospitalId(2, 1);
        assertEquals(1, foundSchedules.size());

        foundSchedules = scheduleService.getSchedulesByDoctorIdAndHospitalId(3, 1);
        assertEquals(0, foundSchedules.size());
    }

    @Test
    void testGetScheduleByDoctorIdAndDayOfWeek() {
        Mockito.when(permanentScheduleRepository.findByDoctorIdAndDayOfWeekAndHospitalId(Mockito.anyInt(),
                Mockito.any(DayOfWeek.class), Mockito.anyInt())).thenAnswer(invocationOnMock -> {
            Optional<Schedule> schedule;
            if (invocationOnMock.getArgument(0).equals(1)
                    && invocationOnMock.getArgument(1).equals(DayOfWeek.MONDAY))
                schedule = Optional.of(scheduleList.get(0));
            else if (invocationOnMock.getArgument(0).equals(1)
                    && invocationOnMock.getArgument(1).equals(DayOfWeek.TUESDAY))
                schedule = Optional.of(scheduleList.get(1));
            else if (invocationOnMock.getArgument(0).equals(2)
                    && invocationOnMock.getArgument(1).equals(DayOfWeek.MONDAY))
                schedule = Optional.of(scheduleList.get(2));
            else schedule = Optional.empty();
            return schedule;
        });

        PermanentScheduleDTO schedule = scheduleService.getScheduleByDoctorIdAndDayOfWeekAndHospitalId(1, DayOfWeek.MONDAY, 1);
        assertEquals(scheduleMapper.permanentScheduleToPermanentScheduleDto((PermanentSchedule) scheduleList.get(0)), schedule);

        schedule = scheduleService.getScheduleByDoctorIdAndDayOfWeekAndHospitalId(1, DayOfWeek.TUESDAY, 1);
        assertEquals(scheduleMapper.permanentScheduleToPermanentScheduleDto((PermanentSchedule) scheduleList.get(1)), schedule);

        schedule = scheduleService.getScheduleByDoctorIdAndDayOfWeekAndHospitalId(2, DayOfWeek.MONDAY, 1);
        assertEquals(scheduleMapper.permanentScheduleToPermanentScheduleDto((PermanentSchedule) scheduleList.get(2)), schedule);

        assertThrows(EntityNotFoundException.class, () -> scheduleService.getScheduleByDoctorIdAndDayOfWeekAndHospitalId(
                1, DayOfWeek.SATURDAY, 1));
    }

    @SneakyThrows
    @Test
    void testSaveSchedule() {
        PermanentScheduleDTO permanentScheduleDTO = scheduleMapper.permanentScheduleToPermanentScheduleDto((PermanentSchedule) scheduleList.get(0));
        Mockito.when(doctorService.getDoctorById(Mockito.anyInt()))
                .thenReturn(peopleMapper.doctorToDoctorDto(scheduleList.get(0).getDoctor()));
        Mockito.when(hospitalService.getHospitalById(Mockito.anyInt()))
                .thenReturn(hospitalMapper.hospitalToHospitalDTO(scheduleList.get(0).getHospital()));
        Mockito.when(permanentScheduleRepository.save(Mockito.any(PermanentSchedule.class))).then(AdditionalAnswers.returnsFirstArg());
        PermanentScheduleDTO newSchedule = scheduleService.savePermanentSchedule(permanentScheduleDTO);
        assertEquals(permanentScheduleDTO, newSchedule);
    }

}
