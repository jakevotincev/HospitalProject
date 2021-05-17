package ru.jakev.hospitalproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jakev.hospitalproject.entities.Appointment;
import ru.jakev.hospitalproject.repositories.AppointmentRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
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
}
