package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.entities.Doctor;
import com.gabrielsilveira.clinica.repositories.DoctorRepository;
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
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Doctor findById(Long id) {
        Optional<Doctor> obj = doctorRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Doctor insert(Doctor obj) {
        return doctorRepository.save(obj);
    }

    public void delete (Long id) {
        try {
            doctorRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Doctor update(Long id, Doctor obj) {
        try {
            Doctor doctor = doctorRepository.getReferenceById(id);
            updateData(doctor, obj);
            return doctorRepository.save(doctor);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Doctor doctor, Doctor obj) {
        doctor.setName(obj.getName());
        doctor.setCrm(obj.getCrm());
        doctor.setEmail(obj.getEmail());
        doctor.setPhone(obj.getPhone());
    }
}
