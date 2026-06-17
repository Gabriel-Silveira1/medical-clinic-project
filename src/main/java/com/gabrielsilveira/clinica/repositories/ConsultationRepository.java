package com.gabrielsilveira.clinica.repositories;

import com.gabrielsilveira.clinica.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    
}
