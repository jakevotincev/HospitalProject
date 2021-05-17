package ru.jakev.hospitalproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jakev.hospitalproject.entities.Appointment;
import ru.jakev.hospitalproject.entities.Schedule;
import ru.jakev.hospitalproject.repositories.AppointmentRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ScheduleService scheduleService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, ScheduleService scheduleService) {
        this.appointmentRepository = appointmentRepository;
        this.scheduleService = scheduleService;
    }

    public List<Appointment> getAppointmentsByDoctorId(Integer id){
       return appointmentRepository.findAllByDoctorId(id);
    }

    public List<Appointment> getAppointmentsByPatientId(Integer id){
        return appointmentRepository.findAllByPatientId(id);
    }

    public List<Appointment> getAppointmentsByDoctorIdAndDateBetween(Integer id, LocalDateTime from, LocalDateTime to){
        return appointmentRepository.findAllByDoctorIdAndDateBetween(id, from, to);
    }

    public Map<LocalTime, Boolean> getScheduleByDoctorIdAndDate(Integer id, LocalDate date){
        Schedule schedule = scheduleService.getScheduleByDoctorIdAndDayOfWeek(id, date.getDayOfWeek());
        Map<LocalTime, Boolean> result = new HashMap<>();
        LocalDateTime dayStart = date.atTime(schedule.getDayStart());
        LocalDateTime dayEnd = date.atTime(schedule.getDayEnd());
        List<Appointment> appointments = appointmentRepository.findAllByDoctorIdAndDateBetween(id, dayStart, dayEnd);
        for (; dayStart.isBefore(dayEnd); dayStart = dayStart.plusHours(1)){
            boolean isTimeBusy = isAppointmentListContainsDate(appointments, dayStart);
            result.put(dayStart.toLocalTime(), isTimeBusy);
        }
        return result;
    }

    public Appointment saveAppointment(Appointment appointment){
        Duration duration = scheduleService.getDurationByDoctorId(appointment.getDoctor().getId());
        appointment.setDuration(duration);
        return appointmentRepository.save(appointment);
    }

    private boolean isAppointmentListContainsDate(List<Appointment> list, LocalDateTime date){
        return list.stream().anyMatch(appointment -> appointment.getDate().equals(date));
    }
}
