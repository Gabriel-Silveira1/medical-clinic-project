package com.gabrielsilveira.clinica.resources;

import com.gabrielsilveira.clinica.dto.AppointmentRequestDTO;
import com.gabrielsilveira.clinica.dto.AppointmentResponseDTO;
import com.gabrielsilveira.clinica.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/appointments")
public class AppointmentResource {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> findAll() {
        List<AppointmentResponseDTO> appointments = appointmentService.findAll();
        return ResponseEntity.ok().body(appointments);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AppointmentResponseDTO> findById(@PathVariable Long id) {
        AppointmentResponseDTO obj = appointmentService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> insert(@RequestBody AppointmentRequestDTO dto) {
        AppointmentResponseDTO obj = appointmentService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AppointmentResponseDTO> update(@PathVariable Long id, @RequestBody AppointmentRequestDTO dto) {
        AppointmentResponseDTO obj = appointmentService.update(id, dto);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}