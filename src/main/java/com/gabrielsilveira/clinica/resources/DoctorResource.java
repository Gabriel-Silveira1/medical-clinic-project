package com.gabrielsilveira.clinica.resources;

import com.gabrielsilveira.clinica.dto.DoctorRequestDTO;
import com.gabrielsilveira.clinica.dto.DoctorResponseDTO;
import com.gabrielsilveira.clinica.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/doctors")
public class DoctorResource {
    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> findAll() {
        List<DoctorResponseDTO> doctors = doctorService.findAll();
        return ResponseEntity.ok().body(doctors);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DoctorResponseDTO> findById(@PathVariable Long id) {
        DoctorResponseDTO obj = doctorService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDTO> insert(@RequestBody DoctorRequestDTO dto) {
        DoctorResponseDTO obj = doctorService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DoctorResponseDTO> update(@PathVariable Long id, @RequestBody DoctorRequestDTO dto) {
        DoctorResponseDTO obj = doctorService.update(id, dto);
        return ResponseEntity.ok().body(obj);
    }
}
