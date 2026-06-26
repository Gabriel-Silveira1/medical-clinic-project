package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.dto.DoctorRequestDTO;
import com.gabrielsilveira.clinica.dto.DoctorResponseDTO;
import com.gabrielsilveira.clinica.entities.Doctor;
import com.gabrielsilveira.clinica.entities.Specialty;
import com.gabrielsilveira.clinica.repositories.DoctorRepository;
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
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    public List<DoctorResponseDTO> findAll() {
        return doctorRepository.findAll()
                .stream()
                .map(DoctorResponseDTO::new)
                .toList();
    }

    public DoctorResponseDTO findById(Long id) {
        Optional<Doctor> obj = doctorRepository.findById(id);
        Doctor doctor = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return new DoctorResponseDTO(doctor);
    }

    public DoctorResponseDTO insert(DoctorRequestDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setCrm(dto.getCrm());
        doctor.setEmail(dto.getEmail());
        doctor.setPhone(dto.getPhone());
        Specialty specialty = specialtyRepository.getReferenceById(dto.getSpecialtyId());
        doctor.setSpecialty(specialty);
        doctor = doctorRepository.save(doctor);
        return new DoctorResponseDTO(doctor);
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

    public DoctorResponseDTO update(Long id, DoctorRequestDTO dto) {
        try {
            Doctor doctor = doctorRepository.getReferenceById(id);
            doctor.setName(dto.getName());
            doctor.setCrm(dto.getCrm());
            doctor.setEmail(dto.getEmail());
            doctor.setPhone(dto.getPhone());
            return new DoctorResponseDTO(doctorRepository.save(doctor));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }
}
