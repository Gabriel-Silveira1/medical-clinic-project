package com.gabrielsilveira.clinica.resources;

import com.gabrielsilveira.clinica.entities.Specialty;
import com.gabrielsilveira.clinica.services.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<Specialty> findById(@PathVariable Long id) {
        Specialty specialty = specialtyService.findById(id);
        return ResponseEntity.ok().body(specialty);
    }

    @PostMapping
    public ResponseEntity<Specialty> insert (@RequestBody Specialty obj) {
        obj =  specialtyService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        specialtyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Specialty> update(@PathVariable Long id, @RequestBody Specialty obj) {
        obj = specialtyService.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}