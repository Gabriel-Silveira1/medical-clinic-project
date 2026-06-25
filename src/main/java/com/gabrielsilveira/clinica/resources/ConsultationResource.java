package com.gabrielsilveira.clinica.resources;

import com.gabrielsilveira.clinica.entities.Consultation;
import com.gabrielsilveira.clinica.services.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping (value = "/{id}")
    public ResponseEntity<Consultation> findById(@PathVariable Long id) {
        Consultation consultation = consultationService.findById(id);
        return ResponseEntity.ok().body(consultation);
    }

    @PostMapping
    public ResponseEntity<Consultation> insert(@RequestBody Consultation obj) {
        obj = consultationService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        consultationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Consultation> update(@PathVariable Long id, @RequestBody Consultation obj) {
        obj = consultationService.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}
