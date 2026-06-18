package com.gabrielsilveira.clinica.resources;

import com.gabrielsilveira.clinica.entities.Payment;
import com.gabrielsilveira.clinica.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/payments")
public class PaymentResource {
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<Payment>> findAll() {
        List<Payment> payments = paymentService.findAll();
        return ResponseEntity.ok().body(payments);
    }
}
