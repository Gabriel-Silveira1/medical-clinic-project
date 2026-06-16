package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.entities.Appointment;
import com.gabrielsilveira.clinica.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }
}