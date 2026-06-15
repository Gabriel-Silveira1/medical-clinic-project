package com.gabrielsilveira.clinica.resources;

import com.gabrielsilveira.clinica.entities.Doctor;
import com.gabrielsilveira.clinica.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/doctors")
public class DoctorResource {
    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<Doctor>> findAll() {
        List<Doctor> doctors = doctorService.findAll();
        return ResponseEntity.ok().body(doctors);
    }
}
