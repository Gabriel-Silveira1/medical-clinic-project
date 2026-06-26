package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.dto.PatientRequestDTO;
import com.gabrielsilveira.clinica.dto.PatientResponseDTO;
import com.gabrielsilveira.clinica.entities.Patient;
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
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<PatientResponseDTO> findAll() {
        return patientRepository.findAll()
                .stream()
                .map(PatientResponseDTO::new)
                .toList();
    }

    public PatientResponseDTO findById(Long id) {
        Optional<Patient> obj = patientRepository.findById(id);
        Patient patient = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return new PatientResponseDTO(patient);
    }

    public PatientResponseDTO insert (PatientRequestDTO dto) {
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setCpf(dto.getCpf());
        patient.setEmail(dto.getEmail());
        patient.setPhone(dto.getPhone());
        patient.setBirthDate(dto.getBirthDate());
        patient = patientRepository.save(patient);
        return new PatientResponseDTO(patient);
    }

    public void delete (Long id) {
        try {
            patientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public PatientResponseDTO update (Long id, PatientRequestDTO dto) {
        try {
            Patient patient = patientRepository.getReferenceById(id);
            patient.setName(dto.getName());
            patient.setEmail(dto.getEmail());
            patient.setPhone(dto.getPhone());
            return new PatientResponseDTO(patientRepository.save(patient));
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }
}
