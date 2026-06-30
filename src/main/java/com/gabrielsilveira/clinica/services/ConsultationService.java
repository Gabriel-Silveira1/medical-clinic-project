package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.dto.ConsultationRequestDTO;
import com.gabrielsilveira.clinica.dto.ConsultationResponseDTO;
import com.gabrielsilveira.clinica.entities.Appointment;
import com.gabrielsilveira.clinica.entities.Consultation;
import com.gabrielsilveira.clinica.repositories.AppointmentRepository;
import com.gabrielsilveira.clinica.repositories.ConsultationRepository;
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
public class ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<ConsultationResponseDTO> findAll() {
        return consultationRepository.findAll()
                .stream()
                .map(ConsultationResponseDTO::new)
                .toList();
    }

    public ConsultationResponseDTO findById(Long id) {
        Optional<Consultation> obj = consultationRepository.findById(id);
        Consultation consultation = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return new ConsultationResponseDTO(consultation);
    }

    public ConsultationResponseDTO insert(ConsultationRequestDTO dto) {
        Consultation consultation = new Consultation();
        consultation.setDiagnosis( dto.getDiagnosis());
        consultation.setPrescription(dto.getPrescription());
        Appointment appointment = appointmentRepository.getReferenceById(dto.getAppointmentId());
        consultation.setAppointment(appointment);
        consultation =  consultationRepository.save(consultation);
        return new ConsultationResponseDTO(consultation);
    }

    public void delete (Long id) {
        try {
            consultationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ConsultationResponseDTO update(Long id, ConsultationRequestDTO dto) {
        try {
            Consultation consultation = consultationRepository.getReferenceById(id);
            consultation.setDiagnosis(dto.getDiagnosis());
            consultation.setPrescription(dto.getPrescription());
            return new ConsultationResponseDTO(consultationRepository.save(consultation));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }
}
