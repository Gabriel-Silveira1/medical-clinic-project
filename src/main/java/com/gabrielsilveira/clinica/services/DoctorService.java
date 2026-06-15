package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.entities.Doctor;
import com.gabrielsilveira.clinica.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }
}
