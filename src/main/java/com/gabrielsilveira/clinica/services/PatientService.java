package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.entities.Patient;
import com.gabrielsilveira.clinica.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient findById(Long id) {
        Optional<Patient> obj = patientRepository.findById(id);
        return obj.get();
    }

    public Patient insert (Patient obj) {
        return patientRepository.save(obj);
    }

    public void delete (Long id) {
        patientRepository.deleteById(id);
    }

    public Patient update (Long id, Patient obj) {
        Patient entity = patientRepository.getReferenceById(id);
        updateData(entity, obj);
        return patientRepository.save(entity);
    }

    public void updateData(Patient entity, Patient obj) {
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
        entity.setPhone(obj.getPhone());
    }
}
