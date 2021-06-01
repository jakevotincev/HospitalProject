package ru.jakev.hospitalproject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jakev.hospitalproject.dto.AppointmentDTO;
import ru.jakev.hospitalproject.dto.DoctorDTO;
import ru.jakev.hospitalproject.dto.PatientDTO;
import ru.jakev.hospitalproject.dto.ScheduleDTO;
import ru.jakev.hospitalproject.entities.Appointment;
import ru.jakev.hospitalproject.entities.Doctor;
import ru.jakev.hospitalproject.entities.DoctorSpeciality;
import ru.jakev.hospitalproject.entities.Patient;
import ru.jakev.hospitalproject.mappers.AppointmentMapper;
import ru.jakev.hospitalproject.repositories.AppointmentRepository;
import ru.jakev.hospitalproject.services.impl.AppointmentServiceImpl;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestAppointmentService {

    @Mock
    AppointmentRepository appointmentRepository;
    @Mock
    ScheduleService scheduleService;

    AppointmentService appointmentService;

    List<Appointment> appointmentList;
    AppointmentMapper appointmentMapper;

    @BeforeEach
    void init() {
        appointmentMapper = Mappers.getMapper(AppointmentMapper.class);
        appointmentService = new AppointmentServiceImpl(appointmentRepository, scheduleService, appointmentMapper);
        appointmentList = new ArrayList<>();
        Doctor doctor1 = new Doctor(1, "surname", "name", "middle_name",
                DoctorSpeciality.DENTIST, null);
        Doctor doctor2 = new Doctor(2, "surname", "name", "middle_name",
                DoctorSpeciality.DENTIST, null);
        Patient patient1 = new Patient(1, "surname", "name", "middle_name", 20);
        Patient patient2 = new Patient(2, "surname", "name", "middle_name", 20);
        appointmentList.add(new Appointment(1, doctor1, patient1, LocalDateTime.of(2021, 5,
                17, 9, 0), Duration.ofHours(1), null, null));
        appointmentList.add(new Appointment(1, doctor1, patient2, LocalDateTime.of(2021, 5,
                17, 15, 0), Duration.ofHours(1), null, null));
        appointmentList.add(new Appointment(1, doctor2, patient2, LocalDateTime.of(2021, 5,
                17, 10, 0), Duration.ofHours(1), null, null));
    }

    @Test
    void testGetAppointmentsByDoctorId() {
        Mockito.when(appointmentRepository.findAllByDoctorId(Mockito.anyInt())).thenAnswer(invocationOnMock -> {
            List<Appointment> appointments;
            switch ((Integer) invocationOnMock.getArgument(0)) {
                case 1:
                    appointments = new ArrayList<>(Arrays.asList(appointmentList.get(0), appointmentList.get(1)));
                    break;
                case 2:
                    appointments = new ArrayList<>(Collections.singletonList(appointmentList.get(2)));
                    break;
                default:
                    appointments = new ArrayList<>();
            }
            return appointments.stream();
        });

        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorId(1);
        assertEquals(2, appointments.size());

        appointments = appointmentService.getAppointmentsByDoctorId(2);
        assertEquals(1, appointments.size());

        appointments = appointmentService.getAppointmentsByDoctorId(5);
        assertTrue(appointments.isEmpty());
    }

    @Test
    void testGetAppointmentsByPatientId() {
        Mockito.when(appointmentRepository.findAllByPatientId(Mockito.anyInt())).thenAnswer(invocationOnMock -> {
            List<Appointment> appointments;
            switch ((Integer) invocationOnMock.getArgument(0)) {
                case 1:
                    appointments = new ArrayList<>(Collections.singletonList(appointmentList.get(0)));
                    break;
                case 2:
                    appointments = new ArrayList<>(Arrays.asList(appointmentList.get(1), appointmentList.get(2)));
                    break;
                default:
                    appointments = new ArrayList<>();
            }
            return appointments.stream();
        });

        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByPatientId(1);
        assertEquals(1, appointments.size());

        appointments = appointmentService.getAppointmentsByPatientId(2);
        assertEquals(2, appointments.size());

        appointments = appointmentService.getAppointmentsByPatientId(4);
        assertTrue(appointments.isEmpty());
    }

    //todo: что вернет метод репозитория при from>to
    @Test
    void testGetAppointmentsByDoctorIdAndDateBetween() {
        mockGetAppointmentsByDoctorIdAndDateBetween();

        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorIdAndDateBetween(1,
                LocalDateTime.of(2021, 5, 17, 9, 0),
                LocalDateTime.of(2021, 5, 17, 18, 0));
        assertEquals(2, appointments.size());

        appointments = appointmentService.getAppointmentsByDoctorIdAndDateBetween(1,
                LocalDateTime.of(2021, 5, 17, 11, 0),
                LocalDateTime.of(2021, 5, 18, 8, 0));
        assertEquals(1, appointments.size());

        appointments = appointmentService.getAppointmentsByDoctorIdAndDateBetween(1,
                LocalDateTime.of(2021, 5, 17, 11, 0),
                LocalDateTime.of(2021, 5, 17, 12, 0));
        assertTrue(appointments.isEmpty());

        appointments = appointmentService.getAppointmentsByDoctorIdAndDateBetween(2,
                LocalDateTime.of(2021, 5, 17, 9, 0),
                LocalDateTime.of(2021, 5, 17, 11, 0));
        assertEquals(1, appointments.size());

        appointments = appointmentService.getAppointmentsByDoctorIdAndDateBetween(2,
                LocalDateTime.of(2021, 5, 20, 9, 0),
                LocalDateTime.of(2021, 5, 21, 9, 0));
        assertTrue(appointments.isEmpty());
    }


    @Test
    void testSaveAppointment() {
        DoctorDTO doctorDTO = new DoctorDTO(1, "surname", "name", "middle_name",
                DoctorSpeciality.DENTIST, null);
        PatientDTO patientDTO = new PatientDTO(1, "surname", "name", "middle_name", 20);
        AppointmentDTO appointmentDTO = new AppointmentDTO(1, doctorDTO, patientDTO,
                LocalDateTime.of(2021, 5, 24, 9, 0), null, null);
        Mockito.when(appointmentRepository.save(Mockito.any(Appointment.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(scheduleService.getScheduleByDoctorIdAndDayOfWeek(Mockito.anyInt(), Mockito.any(DayOfWeek.class))).thenReturn(
                new ScheduleDTO(1, 1, DayOfWeek.MONDAY,
                        LocalTime.of(9, 0), LocalTime.of(18, 0), Duration.ofHours(2), null));
        AppointmentDTO newAppointmentDTO = appointmentService.saveAppointment(appointmentDTO);
        appointmentDTO.setDuration(Duration.ofHours(2));
        assertEquals(appointmentDTO, newAppointmentDTO);
    }

    @Test
    void testGetScheduleByDoctorIdAndDate() {
        mockGetAppointmentsByDoctorIdAndDateBetween();
        Mockito.when(scheduleService.getScheduleByDoctorIdAndDayOfWeek(Mockito.anyInt(), Mockito.any(DayOfWeek.class)))
                .thenReturn(new ScheduleDTO(1, 1, DayOfWeek.MONDAY,
                        LocalTime.of(9, 0), LocalTime.of(18, 0), Duration.ofHours(2), null));
        Map<LocalTime, Boolean> schedule = appointmentService.getScheduleByDoctorIdAndDate(1,
                LocalDate.of(2021, 5, 17));

        for (Map.Entry<LocalTime, Boolean> entry : schedule.entrySet()) {
            if (entry.getKey().equals(LocalTime.of(9, 0)) || entry.getKey().equals(LocalTime.of(15, 0)))
                assertTrue(entry.getValue());
            else assertFalse(entry.getValue());
        }
        Mockito.when(scheduleService.getScheduleByDoctorIdAndDayOfWeek(Mockito.anyInt(), Mockito.any(DayOfWeek.class)))
                .thenReturn(new ScheduleDTO(1, 1, DayOfWeek.MONDAY,
                        LocalTime.of(12, 0), LocalTime.of(18, 0), Duration.ofHours(1), null));
        schedule = appointmentService.getScheduleByDoctorIdAndDate(1,
                LocalDate.of(2021, 5, 17));

        for (Map.Entry<LocalTime, Boolean> entry : schedule.entrySet()) {
            if (entry.getKey().equals(LocalTime.of(15, 0)))
                assertTrue(entry.getValue());
            else assertFalse(entry.getValue());
        }

    }

    private void mockGetAppointmentsByDoctorIdAndDateBetween() {
        Mockito.when(appointmentRepository.findAllByDoctorIdAndDateBetween(Mockito.anyInt(),
                Mockito.any(LocalDateTime.class),
                Mockito.any(LocalDateTime.class))).thenAnswer(invocationOnMock -> {
            List<Appointment> appointments;
            Integer id = invocationOnMock.getArgument(0);
            LocalDateTime from = invocationOnMock.getArgument(1);
            LocalDateTime to = invocationOnMock.getArgument(2);

            if (id == 1) {
                if (from.isBefore(LocalDateTime.of(2021, 5, 17, 9, 0, 1)) &&
                        to.isAfter(LocalDateTime.of(2021, 5, 17, 15, 0, 1))) {
                    appointments = new ArrayList<>(Arrays.asList(appointmentList.get(0), appointmentList.get(1)));
                } else if (from.isAfter(LocalDateTime.of(2021, 5, 17, 9, 0)) &&
                        from.isBefore(LocalDateTime.of(2021, 5, 17, 15, 0)) &&
                        to.isAfter(LocalDateTime.of(2021, 5, 17, 15, 0))) {
                    appointments = new ArrayList<>(Collections.singletonList(appointmentList.get(1)));
                } else appointments = new ArrayList<>();
            } else if (id == 2) {
                if (from.isBefore(LocalDateTime.of(2021, 5, 17, 10, 0)) &&
                        to.isAfter(LocalDateTime.of(2021, 5, 17, 10, 0)))
                    appointments = new ArrayList<>(Collections.singletonList(appointmentList.get(2)));
                else appointments = new ArrayList<>();
            } else appointments = new ArrayList<>();

            return appointments.stream();
        });
    }
}
