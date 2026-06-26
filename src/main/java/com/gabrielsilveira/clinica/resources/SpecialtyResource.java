package com.gabrielsilveira.clinica.resources;

import com.gabrielsilveira.clinica.dto.SpecialtyRequestDTO;
import com.gabrielsilveira.clinica.dto.SpecialtyResponseDTO;
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
    public ResponseEntity<List<SpecialtyResponseDTO>> findAll() {
        List<SpecialtyResponseDTO> specialties = specialtyService.findAll();
        return ResponseEntity.ok().body(specialties);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SpecialtyResponseDTO> findById(@PathVariable Long id) {
        SpecialtyResponseDTO obj = specialtyService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<SpecialtyResponseDTO> insert(@RequestBody SpecialtyRequestDTO dto) {
        SpecialtyResponseDTO obj = specialtyService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<SpecialtyResponseDTO> update(@PathVariable Long id, @RequestBody SpecialtyRequestDTO dto) {
        SpecialtyResponseDTO obj = specialtyService.update(id, dto);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        specialtyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}