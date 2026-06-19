package com.gabrielsilveira.clinica.resources;

import com.gabrielsilveira.clinica.entities.Patient;
import com.gabrielsilveira.clinica.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/patients")
public class PatientResource {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> findAll() {
        List<Patient> patients = patientService.findAll();
        return ResponseEntity.ok().body(patients);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Patient> findById(@PathVariable Long id) {
        Patient obj = patientService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Patient> insert(@RequestBody Patient obj) {
        obj = patientService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Patient> update(@PathVariable Long id, @RequestBody Patient obj) {
        obj = patientService.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}