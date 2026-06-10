package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.entities.Patient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    public List<Patient> findAll() {
        return List.of();
    }
}
