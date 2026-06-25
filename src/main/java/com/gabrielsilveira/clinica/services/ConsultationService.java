package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.entities.Consultation;
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

    public List<Consultation> findAll() {
        return consultationRepository.findAll();
    }

    public Consultation findById(Long id) {
        Optional<Consultation> obj = consultationRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Consultation insert(Consultation obj) {
        return consultationRepository.save(obj);
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

    public Consultation update(Long id, Consultation obj) {
        try {
            Consultation consultation = consultationRepository.getReferenceById(id);
            updateData(consultation, obj);
            return consultationRepository.save(consultation);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Consultation consultation, Consultation obj) {
        consultation.setDiagnosis(obj.getDiagnosis());
        consultation.setPrescription(obj.getPrescription());
    }
}
