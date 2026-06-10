package com.gabrielsilveira.clinica.resources;

import com.gabrielsilveira.clinica.entities.Patient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/patients")
public class PatientResource {
    @GetMapping
    public ResponseEntity<Patient> findAll() {
        Patient p = new Patient(1L, "João Silva", "123.456.789-00", "joaosilva@gmail.com", "(17) 99123-4567", LocalDate.of(1996, 9, 17));
        return ResponseEntity.ok().body(p);
    }
}
