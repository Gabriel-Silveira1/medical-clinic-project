package com.gabrielsilveira.clinica.services;

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

    public List<Specialty> findAll() {
        return specialtyRepository.findAll();
    }

    public Specialty findById(Long id) {
        Optional<Specialty> obj = specialtyRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Specialty insert(Specialty obj) {
        return specialtyRepository.save(obj);
    }

    public void delete (Long id) {
        try {
            specialtyRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Specialty update(Long id, Specialty obj) {
        try {
            Specialty specialty = specialtyRepository.getReferenceById(id);
            updateData(specialty, obj);
            return specialtyRepository.save(specialty);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Specialty specialty, Specialty obj) {
        specialty.setName(obj.getName());
        specialty.setDescription(obj.getDescription());
    }
}