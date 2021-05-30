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
import ru.jakev.hospitalproject.dto.ScheduleDTO;
import ru.jakev.hospitalproject.entities.Doctor;
import ru.jakev.hospitalproject.entities.DoctorSpeciality;
import ru.jakev.hospitalproject.entities.Schedule;
import ru.jakev.hospitalproject.mappers.PeopleMapper;
import ru.jakev.hospitalproject.mappers.ScheduleMapper;
import ru.jakev.hospitalproject.repositories.ScheduleRepository;
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
    ScheduleRepository scheduleRepository;
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
        scheduleService = new ScheduleServiceImpl(scheduleRepository, doctorService, scheduleMapper, peopleMapper);
        Doctor doctor = new Doctor(1, "surname", "name", "middle_name",
                DoctorSpeciality.DENTIST, 10);
        Doctor doctor2 = new Doctor(2, "surname", "name", "middle_name",
                DoctorSpeciality.DENTIST, 10);
        scheduleList.add(new Schedule(1, doctor, DayOfWeek.MONDAY, LocalTime.of(9, 0),
                LocalTime.of(18, 0), Duration.ofHours(1)));
        scheduleList.add(new Schedule(2, doctor, DayOfWeek.TUESDAY, LocalTime.of(9, 0),
                LocalTime.of(18, 0), Duration.ofHours(1)));
        scheduleList.add(new Schedule(3, doctor2, DayOfWeek.MONDAY, LocalTime.of(9, 0),
                LocalTime.of(18, 0), Duration.ofHours(1)));
    }

    @Test
    void testGetSchedulesByDoctorId() {
        Mockito.when(scheduleRepository.findAllByDoctorId(Mockito.anyInt())).thenAnswer(invocationOnMock -> {
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

        List<ScheduleDTO> foundSchedules = scheduleService.getSchedulesByDoctorId(1);
        assertEquals(2, foundSchedules.size());

        foundSchedules = scheduleService.getSchedulesByDoctorId(2);
        assertEquals(1, foundSchedules.size());

        foundSchedules = scheduleService.getSchedulesByDoctorId(3);
        assertEquals(0, foundSchedules.size());
    }

    @Test
    void testGetScheduleByDoctorIdAndDayOfWeek() {
        Mockito.when(scheduleRepository.findByDoctorIdAndDayOfWeek(Mockito.anyInt(), Mockito.any(DayOfWeek.class)))
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

        ScheduleDTO schedule = scheduleService.getScheduleByDoctorIdAndDayOfWeek(1, DayOfWeek.MONDAY);
        assertEquals(scheduleMapper.scheduleToScheduleDto(scheduleList.get(0)), schedule);

        schedule = scheduleService.getScheduleByDoctorIdAndDayOfWeek(1, DayOfWeek.TUESDAY);
        assertEquals(scheduleMapper.scheduleToScheduleDto(scheduleList.get(1)), schedule);

        schedule = scheduleService.getScheduleByDoctorIdAndDayOfWeek(2, DayOfWeek.MONDAY);
        assertEquals(scheduleMapper.scheduleToScheduleDto(scheduleList.get(2)), schedule);

        assertThrows(EntityNotFoundException.class, () -> scheduleService.getScheduleByDoctorIdAndDayOfWeek(1, DayOfWeek.SATURDAY));
    }

    @SneakyThrows
    @Test
    void testSaveSchedule(){
        ScheduleDTO scheduleDTO = scheduleMapper.scheduleToScheduleDto(scheduleList.get(0));
        scheduleDTO.setDoctorId(1);
        Mockito.when(doctorService.getDoctorById(Mockito.anyInt()))
                .thenReturn(peopleMapper.doctorToDoctorDto(scheduleList.get(0).getDoctor()));
        Mockito.when(scheduleRepository.save(Mockito.any(Schedule.class))).then(AdditionalAnswers.returnsFirstArg());
        ScheduleDTO newSchedule = scheduleService.saveSchedule(scheduleDTO);
        assertEquals(scheduleDTO, newSchedule);
    }

}
