package com.gabrielsilveira.clinica.resources;

import com.gabrielsilveira.clinica.dto.PaymentRequestDTO;
import com.gabrielsilveira.clinica.dto.PaymentResponseDTO;
import com.gabrielsilveira.clinica.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/payments")
public class PaymentResource {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> findAll() {
        List<PaymentResponseDTO> payments = paymentService.findAll();
        return ResponseEntity.ok().body(payments);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PaymentResponseDTO> findById(@PathVariable Long id) {
        PaymentResponseDTO obj = paymentService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> insert(@RequestBody PaymentRequestDTO dto) {
        PaymentResponseDTO obj = paymentService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PaymentResponseDTO> update(@PathVariable Long id, @RequestBody PaymentRequestDTO dto) {
        PaymentResponseDTO obj = paymentService.update(id, dto);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}