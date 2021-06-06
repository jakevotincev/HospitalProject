package ru.jakev.hospitalproject.services;

import static org.junit.jupiter.api.Assertions.*;

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
import ru.jakev.hospitalproject.entities.Doctor;
import ru.jakev.hospitalproject.entities.DoctorSpeciality;
import ru.jakev.hospitalproject.entities.PermanentSchedule;
import ru.jakev.hospitalproject.entities.Schedule;
import ru.jakev.hospitalproject.mappers.PeopleMapper;
import ru.jakev.hospitalproject.mappers.ScheduleMapper;
import ru.jakev.hospitalproject.repositories.PermanentScheduleRepository;
import ru.jakev.hospitalproject.services.impl.ScheduleServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TestScheduleService {

    ScheduleService scheduleService;
    @Mock
    PermanentScheduleRepository permanentScheduleRepository;
    @Mock
    DoctorService doctorService;
    List<Schedule> scheduleList = new ArrayList<>();
    ScheduleMapper scheduleMapper;
    PeopleMapper peopleMapper;
    private final static Logger LOGGER = LoggerFactory.getLogger(TestScheduleService.class);


    @BeforeEach
    void init() {
        scheduleMapper = Mappers.getMapper(ScheduleMapper.class);
        peopleMapper = Mappers.getMapper(PeopleMapper.class);
        scheduleService = new ScheduleServiceImpl(permanentScheduleRepository, doctorService, scheduleMapper, peopleMapper);
        Doctor doctor = new Doctor(1, "surname", "name", "middle_name",
                DoctorSpeciality.DENTIST, null);
        Doctor doctor2 = new Doctor(2, "surname", "name", "middle_name",
                DoctorSpeciality.DENTIST, null);
        scheduleList.add(new PermanentSchedule(1, doctor, LocalTime.of(9, 0),
                LocalTime.of(18, 0), Duration.ofHours(1), null, DayOfWeek.MONDAY));
        scheduleList.add(new PermanentSchedule(2, doctor, LocalTime.of(9, 0),
                LocalTime.of(18, 0), Duration.ofHours(1), null, DayOfWeek.TUESDAY));
        scheduleList.add(new PermanentSchedule(3, doctor2, LocalTime.of(9, 0),
                LocalTime.of(18, 0), Duration.ofHours(1), null, DayOfWeek.MONDAY));
    }

    @SneakyThrows
    @Test
    void testGetSchedulesByDoctorId() {
        Mockito.when(permanentScheduleRepository.findAllByDoctorId(Mockito.anyInt())).thenAnswer(invocationOnMock -> {
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

        List<PermanentScheduleDTO> foundSchedules = scheduleService.getSchedulesByDoctorId(1);
        assertEquals(2, foundSchedules.size());

        foundSchedules = scheduleService.getSchedulesByDoctorId(2);
        assertEquals(1, foundSchedules.size());

        foundSchedules = scheduleService.getSchedulesByDoctorId(3);
        assertEquals(0, foundSchedules.size());
    }

    @Test
    void testGetScheduleByDoctorIdAndDayOfWeek() {
        Mockito.when(permanentScheduleRepository.findByDoctorIdAndDayOfWeek(Mockito.anyInt(), Mockito.any(DayOfWeek.class)))
                .thenAnswer(invocationOnMock -> {
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

        PermanentScheduleDTO schedule = scheduleService.getScheduleByDoctorIdAndDayOfWeek(1, DayOfWeek.MONDAY);
        assertEquals(scheduleMapper.scheduleToScheduleDto((PermanentSchedule) scheduleList.get(0)), schedule);

        schedule = scheduleService.getScheduleByDoctorIdAndDayOfWeek(1, DayOfWeek.TUESDAY);
        assertEquals(scheduleMapper.scheduleToScheduleDto((PermanentSchedule) scheduleList.get(1)), schedule);

        schedule = scheduleService.getScheduleByDoctorIdAndDayOfWeek(2, DayOfWeek.MONDAY);
        assertEquals(scheduleMapper.scheduleToScheduleDto((PermanentSchedule) scheduleList.get(2)), schedule);

        assertThrows(EntityNotFoundException.class, () -> scheduleService.getScheduleByDoctorIdAndDayOfWeek(1, DayOfWeek.SATURDAY));
    }

    @SneakyThrows
    @Test
    void testSaveSchedule() {
        PermanentScheduleDTO permanentScheduleDTO = scheduleMapper.scheduleToScheduleDto((PermanentSchedule) scheduleList.get(0));
        permanentScheduleDTO.setDoctorId(1);
        Mockito.when(doctorService.getDoctorById(Mockito.anyInt()))
                .thenReturn(peopleMapper.doctorToDoctorDto(scheduleList.get(0).getDoctor()));
        Mockito.when(permanentScheduleRepository.save(Mockito.any(PermanentSchedule.class))).then(AdditionalAnswers.returnsFirstArg());
        PermanentScheduleDTO newSchedule = scheduleService.saveSchedule(permanentScheduleDTO);
        assertEquals(permanentScheduleDTO, newSchedule);
    }

}
