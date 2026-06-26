package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.dto.SpecialtyRequestDTO;
import com.gabrielsilveira.clinica.dto.SpecialtyResponseDTO;
import com.gabrielsilveira.clinica.entities.Specialty;
import com.gabrielsilveira.clinica.repositories.SpecialtyRepository;
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
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    public List<SpecialtyResponseDTO> findAll() {
        return specialtyRepository.findAll()
                .stream()
                .map(SpecialtyResponseDTO::new)
                .toList();
    }

    public SpecialtyResponseDTO findById(Long id) {
        Optional<Specialty> obj = specialtyRepository.findById(id);
        Specialty specialty = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return new SpecialtyResponseDTO(specialty);
    }

    public SpecialtyResponseDTO insert(SpecialtyRequestDTO dto) {
        Specialty specialty = new Specialty();
        specialty.setName(dto.getName());
        specialty.setDescription(dto.getDescription());
        specialty = specialtyRepository.save(specialty);
        return new SpecialtyResponseDTO(specialty);
    }

    public SpecialtyResponseDTO update(Long id, SpecialtyRequestDTO dto) {
        try {
            Specialty specialty = specialtyRepository.getReferenceById(id);
            specialty.setName(dto.getName());
            specialty.setDescription(dto.getDescription());
            return new SpecialtyResponseDTO(specialtyRepository.save(specialty));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public void delete(Long id) {
        try {
            specialtyRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}