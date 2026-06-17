package com.gabrielsilveira.clinica.resources;

import com.gabrielsilveira.clinica.entities.Consultation;
import com.gabrielsilveira.clinica.services.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/consultations")
public class ConsultationResource {
    @Autowired
    private ConsultationService consultationService;

    @GetMapping
    public ResponseEntity<List<Consultation>> findAll() {
        List<Consultation> consultations = consultationService.findAll();
        return ResponseEntity.ok().body(consultations);
    }
}
