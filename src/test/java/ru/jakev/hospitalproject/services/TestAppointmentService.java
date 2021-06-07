package ru.jakev.hospitalproject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jakev.hospitalproject.dto.*;
import ru.jakev.hospitalproject.entities.*;
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
        Hospital hospital = new Hospital(1, "hospital", "street", Set.of(doctor1, doctor2));
        doctor1.setHospitals(Collections.singleton(hospital));
        doctor2.setHospitals(Collections.singleton(hospital));
        Patient patient1 = new Patient(1, "surname", "name", "middle_name", 20);
        Patient patient2 = new Patient(2, "surname", "name", "middle_name", 20);
        appointmentList.add(new Appointment(1, doctor1, patient1, LocalDateTime.of(2021, 5,
                17, 9, 0), Duration.ofHours(1), hospital, null));
        appointmentList.add(new Appointment(1, doctor1, patient2, LocalDateTime.of(2021, 5,
                17, 15, 0), Duration.ofHours(1), hospital, null));
        appointmentList.add(new Appointment(1, doctor2, patient2, LocalDateTime.of(2021, 5,
                17, 10, 0), Duration.ofHours(1), hospital, null));
    }

    @Test
    void testGetAppointmentsByDoctorId() {
        Mockito.when(appointmentRepository.findAllByDoctorIdAndHospitalId(Mockito.anyInt(), Mockito.anyInt())).thenAnswer(invocationOnMock -> {
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

        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorIdAndHospitalId(1, 1);
        assertEquals(2, appointments.size());

        appointments = appointmentService.getAppointmentsByDoctorIdAndHospitalId(2, 1);
        assertEquals(1, appointments.size());

        appointments = appointmentService.getAppointmentsByDoctorIdAndHospitalId(5, 1);
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

        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorIdAndHospitalIdAndDateBetween(1, 1,
                LocalDateTime.of(2021, 5, 17, 9, 0),
                LocalDateTime.of(2021, 5, 17, 18, 0));
        assertEquals(2, appointments.size());

        appointments = appointmentService.getAppointmentsByDoctorIdAndHospitalIdAndDateBetween(1, 1,
                LocalDateTime.of(2021, 5, 17, 11, 0),
                LocalDateTime.of(2021, 5, 18, 8, 0));
        assertEquals(1, appointments.size());

        appointments = appointmentService.getAppointmentsByDoctorIdAndHospitalIdAndDateBetween(1, 1,
                LocalDateTime.of(2021, 5, 17, 11, 0),
                LocalDateTime.of(2021, 5, 17, 12, 0));
        assertTrue(appointments.isEmpty());

        appointments = appointmentService.getAppointmentsByDoctorIdAndHospitalIdAndDateBetween(2, 1,
                LocalDateTime.of(2021, 5, 17, 9, 0),
                LocalDateTime.of(2021, 5, 17, 11, 0));
        assertEquals(1, appointments.size());

        appointments = appointmentService.getAppointmentsByDoctorIdAndHospitalIdAndDateBetween(2, 1,
                LocalDateTime.of(2021, 5, 20, 9, 0),
                LocalDateTime.of(2021, 5, 21, 9, 0));
        assertTrue(appointments.isEmpty());
    }


    @Test
    void testSaveAppointment() {
        HospitalDTO hospitalDTO = new HospitalDTO(1, "hospital", "address");
        DoctorDTO doctorDTO = new DoctorDTO(1, "surname", "name", "middle_name",
                DoctorSpeciality.DENTIST, Set.of(hospitalDTO));
        PatientDTO patientDTO = new PatientDTO(1, "surname", "name", "middle_name", 20);
        AppointmentDTO appointmentDTO = new AppointmentDTO(1, doctorDTO, patientDTO,
                LocalDateTime.of(2021, 5, 24, 9, 0), hospitalDTO, null);
        Mockito.when(appointmentRepository.save(Mockito.any(Appointment.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(scheduleService.getScheduleByDoctorIdAndDayOfWeekAndHospitalId(Mockito.anyInt(),
                Mockito.any(DayOfWeek.class), Mockito.anyInt())).thenReturn(
                new PermanentScheduleDTO(1, 1,
                        LocalTime.of(9, 0), LocalTime.of(18, 0), Duration.ofHours(2), null, DayOfWeek.MONDAY));
        AppointmentDTO newAppointmentDTO = appointmentService.saveAppointment(appointmentDTO);
        appointmentDTO.setDuration(Duration.ofHours(2));
        assertEquals(appointmentDTO, newAppointmentDTO);
    }

    @Test
    void testGetScheduleByDoctorIdAndDate() {
        mockGetAppointmentsByDoctorIdAndDateBetween();
        Mockito.when(scheduleService.getScheduleByDoctorIdAndDayOfWeekAndHospitalId(Mockito.anyInt(),
                Mockito.any(DayOfWeek.class), Mockito.anyInt()))
                .thenReturn(new PermanentScheduleDTO(1, 1,
                        LocalTime.of(9, 0), LocalTime.of(18, 0), Duration.ofHours(2), null, DayOfWeek.MONDAY));
        Map<LocalTime, Boolean> schedule = appointmentService.getScheduleByDoctorIdAndDateAndHospitalId(1,
                LocalDate.of(2021, 5, 17), 1);

        for (Map.Entry<LocalTime, Boolean> entry : schedule.entrySet()) {
            if (entry.getKey().equals(LocalTime.of(9, 0)) || entry.getKey().equals(LocalTime.of(15, 0)))
                assertTrue(entry.getValue());
            else assertFalse(entry.getValue());
        }
        Mockito.when(scheduleService.getScheduleByDoctorIdAndDayOfWeekAndHospitalId(Mockito.anyInt(),
                Mockito.any(DayOfWeek.class), Mockito.anyInt()))
                .thenReturn(new PermanentScheduleDTO(1, 1,
                        LocalTime.of(12, 0), LocalTime.of(18, 0), Duration.ofHours(1), null, DayOfWeek.MONDAY));
        schedule = appointmentService.getScheduleByDoctorIdAndDateAndHospitalId(1,
                LocalDate.of(2021, 5, 17), 1);

        for (Map.Entry<LocalTime, Boolean> entry : schedule.entrySet()) {
            if (entry.getKey().equals(LocalTime.of(15, 0)))
                assertTrue(entry.getValue());
            else assertFalse(entry.getValue());
        }

    }

    private void mockGetAppointmentsByDoctorIdAndDateBetween() {
        Mockito.when(appointmentRepository.findAllByDoctorIdAndHospitalIdAndDateBetween(Mockito.anyInt(), Mockito.anyInt(),
                Mockito.any(LocalDateTime.class),
                Mockito.any(LocalDateTime.class))).thenAnswer(invocationOnMock -> {
            List<Appointment> appointments;
            Integer doctorId = invocationOnMock.getArgument(0);
            LocalDateTime from = invocationOnMock.getArgument(2);
            LocalDateTime to = invocationOnMock.getArgument(3);

            if (doctorId == 1) {
                if (from.isBefore(LocalDateTime.of(2021, 5, 17, 9, 0, 1)) &&
                        to.isAfter(LocalDateTime.of(2021, 5, 17, 15, 0, 1))) {
                    appointments = new ArrayList<>(Arrays.asList(appointmentList.get(0), appointmentList.get(1)));
                } else if (from.isAfter(LocalDateTime.of(2021, 5, 17, 9, 0)) &&
                        from.isBefore(LocalDateTime.of(2021, 5, 17, 15, 0)) &&
                        to.isAfter(LocalDateTime.of(2021, 5, 17, 15, 0))) {
                    appointments = new ArrayList<>(Collections.singletonList(appointmentList.get(1)));
                } else appointments = new ArrayList<>();
            } else if (doctorId == 2) {
                if (from.isBefore(LocalDateTime.of(2021, 5, 17, 10, 0)) &&
                        to.isAfter(LocalDateTime.of(2021, 5, 17, 10, 0)))
                    appointments = new ArrayList<>(Collections.singletonList(appointmentList.get(2)));
                else appointments = new ArrayList<>();
            } else appointments = new ArrayList<>();

            return appointments.stream();
        });
    }
}
