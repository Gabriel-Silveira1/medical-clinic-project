package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.entities.Consultation;
import com.gabrielsilveira.clinica.repositories.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;

    public List<Consultation> findAll() {
        return consultationRepository.findAll();
    }
}
