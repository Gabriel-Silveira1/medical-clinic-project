package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.entities.Specialty;
import com.gabrielsilveira.clinica.repositories.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    public List<Specialty> findAll() {
        return specialtyRepository.findAll();
    }
}