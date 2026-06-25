package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.entities.Appointment;
import com.gabrielsilveira.clinica.repositories.AppointmentRepository;
import com.gabrielsilveira.clinica.services.exceptions.DatabaseException;
import com.gabrielsilveira.clinica.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Appointment findById(Long id) {
        Optional<Appointment> obj = appointmentRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Appointment insert(Appointment obj) {
        return appointmentRepository.save(obj);
    }

    public Appointment delete (Long id) {
        try {
            appointmentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DatabaseException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Appointment update(Long id, Appointment obj) {
        try {
            Appointment appointment = appointmentRepository.getReferenceById(id);
            updateData(appointment, obj);
            return appointmentRepository.save(appointment);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Appointment appointment, Appointment obj) {
        appointment.setMoment(obj.getMoment());
        appointment.setConsultation(obj.getConsultation());
        appointment.setDoctor(obj.getDoctor());
    }
}