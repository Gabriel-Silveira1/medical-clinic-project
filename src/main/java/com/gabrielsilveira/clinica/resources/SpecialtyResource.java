package com.gabrielsilveira.clinica.resources;

import com.gabrielsilveira.clinica.entities.Specialty;
import com.gabrielsilveira.clinica.services.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/specialties")
public class SpecialtyResource {

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping
    public ResponseEntity<List<Specialty>> findAll() {
        List<Specialty> specialties = specialtyService.findAll();
        return ResponseEntity.ok().body(specialties);
    }
}