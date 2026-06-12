package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.entities.Patient;
import com.gabrielsilveira.clinica.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }
}
