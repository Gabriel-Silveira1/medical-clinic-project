package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.dto.AppointmentRequestDTO;
import com.gabrielsilveira.clinica.dto.AppointmentResponseDTO;
import com.gabrielsilveira.clinica.entities.Appointment;
import com.gabrielsilveira.clinica.entities.Doctor;
import com.gabrielsilveira.clinica.entities.Patient;
import com.gabrielsilveira.clinica.repositories.AppointmentRepository;
import com.gabrielsilveira.clinica.repositories.DoctorRepository;
import com.gabrielsilveira.clinica.repositories.PatientRepository;
import com.gabrielsilveira.clinica.services.exceptions.DatabaseException;
import com.gabrielsilveira.clinica.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<AppointmentResponseDTO> findAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(AppointmentResponseDTO::new)
                .toList();
    }

    public AppointmentResponseDTO findById(Long id) {
        Optional<Appointment> obj = appointmentRepository.findById(id);
        Appointment appointment = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return new AppointmentResponseDTO(appointment);
    }

    public AppointmentResponseDTO insert(AppointmentRequestDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setMoment(dto.getMoment());
        appointment.setStatus(dto.getStatus());
        Patient patient = patientRepository.getReferenceById(dto.getPatientId());
        appointment.setPatient(patient);
        Doctor doctor = doctorRepository.getReferenceById(dto.getDoctorId());
        appointment.setDoctor(doctor);
        appointment = appointmentRepository.save(appointment);
        return new AppointmentResponseDTO(appointment);
    }

    public AppointmentResponseDTO update(Long id, AppointmentRequestDTO dto) {
        try {
            Appointment appointment = appointmentRepository.getReferenceById(id);
            appointment.setMoment(dto.getMoment());
            appointment.setStatus(dto.getStatus());
            return new AppointmentResponseDTO(appointmentRepository.save(appointment));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public void delete(Long id) {
        try {
            appointmentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}