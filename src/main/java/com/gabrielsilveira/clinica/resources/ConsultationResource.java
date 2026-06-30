package com.gabrielsilveira.clinica.resources;

import com.gabrielsilveira.clinica.dto.ConsultationRequestDTO;
import com.gabrielsilveira.clinica.dto.ConsultationResponseDTO;
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
    public ResponseEntity<List<ConsultationResponseDTO>> findAll() {
        List<ConsultationResponseDTO> consultations = consultationService.findAll();
        return ResponseEntity.ok().body(consultations);
    }

    @GetMapping (value = "/{id}")
    public ResponseEntity<ConsultationResponseDTO> findById(@PathVariable Long id) {
        ConsultationResponseDTO consultation = consultationService.findById(id);
        return ResponseEntity.ok().body(consultation);
    }

    @PostMapping
    public ResponseEntity<ConsultationResponseDTO> insert(@RequestBody ConsultationRequestDTO dto) {
        ConsultationResponseDTO obj = consultationService.insert(dto);
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
    public ResponseEntity<ConsultationResponseDTO> update(@PathVariable Long id, @RequestBody ConsultationRequestDTO dto) {
        ConsultationResponseDTO obj  = consultationService.update(id, dto);
        return ResponseEntity.ok().body(obj);
    }
}
