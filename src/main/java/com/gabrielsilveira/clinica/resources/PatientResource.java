package com.gabrielsilveira.clinica.resources;

import com.gabrielsilveira.clinica.dto.PatientRequestDTO;
import com.gabrielsilveira.clinica.dto.PatientResponseDTO;
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
    public ResponseEntity<List<PatientResponseDTO>> findAll() {
        List<PatientResponseDTO> patients = patientService.findAll();
        return ResponseEntity.ok().body(patients);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PatientResponseDTO> findById(@PathVariable Long id) {
        PatientResponseDTO obj = patientService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> insert(@RequestBody PatientRequestDTO dto) {
        PatientResponseDTO obj = patientService.insert(dto);
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
    public ResponseEntity<PatientResponseDTO> update(@PathVariable Long id, @RequestBody PatientRequestDTO dto) {
        PatientResponseDTO obj = patientService.update(id, dto);
        return ResponseEntity.ok().body(obj);
    }
}