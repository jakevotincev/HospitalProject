//package ru.jakev.hospitalproject.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import ru.jakev.hospitalproject.dto.ScheduleDTO;
//import ru.jakev.hospitalproject.entities.Appointment;
//import ru.jakev.hospitalproject.entities.Schedule;
//import ru.jakev.hospitalproject.repositories.AppointmentRepository;
//
//import javax.persistence.EntityNotFoundException;
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class AppointmentService {
//
//    private final AppointmentRepository appointmentRepository;
//    private final ScheduleService scheduleService;
//
//    @Autowired
//    public AppointmentService(AppointmentRepository appointmentRepository, ScheduleService scheduleService) {
//        this.appointmentRepository = appointmentRepository;
//        this.scheduleService = scheduleService;
//    }
//
//    public List<Appointment> getAppointmentsByDoctorId(Integer id) {
//        return appointmentRepository.findAllByDoctorId(id).collect(Collectors.toList());
//    }
//
//    public List<Appointment> getAppointmentsByPatientId(Integer id) {
//        return appointmentRepository.findAllByPatientId(id).collect(Collectors.toList());
//    }
//
//    public List<Appointment> getAppointmentsByDoctorIdAndDateBetween(Integer id, LocalDateTime from, LocalDateTime to) {
//        return appointmentRepository.findAllByDoctorIdAndDateBetween(id, from, to).collect(Collectors.toList());
//    }
//
//    public Map<LocalTime, Boolean> getScheduleByDoctorIdAndDate(Integer id, LocalDate date) {
//        ScheduleDTO schedule = scheduleService.getScheduleByDoctorIdAndDayOfWeek(id, date.getDayOfWeek());
//        Map<LocalTime, Boolean> result = new HashMap<>();
//        LocalDateTime dayStart = date.atTime(schedule.getDayStart());
//        LocalDateTime dayEnd = date.atTime(schedule.getDayEnd());
//        List<Appointment> appointments = appointmentRepository.findAllByDoctorIdAndDateBetween(id, dayStart, dayEnd)
//                .collect(Collectors.toList());
//        for (; dayStart.isBefore(dayEnd); dayStart = dayStart.plusHours(1)) {
//            boolean isTimeBusy = isAppointmentListContainsDate(appointments, dayStart);
//            result.put(dayStart.toLocalTime(), isTimeBusy);
//        }
//        return result;
//    }
//
//    public Appointment saveAppointment(Appointment appointment) {
//        Integer doctorId = appointment.getDoctor().getId();
//        Duration duration = scheduleService.getSchedulesByDoctorId(doctorId)
//                .stream().findFirst().orElseThrow(() ->
//                        new EntityNotFoundException("Not found schedules with doctor_id: " + doctorId)).getDuration();
//        appointment.setDuration(duration);
//        return appointmentRepository.save(appointment);
//    }
//
//    private boolean isAppointmentListContainsDate(List<Appointment> list, LocalDateTime date) {
//        return list.stream().anyMatch(appointment -> appointment.getDate().equals(date));
//    }
//}
