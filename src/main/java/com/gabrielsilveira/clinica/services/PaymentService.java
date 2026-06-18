package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.entities.Payment;
import com.gabrielsilveira.clinica.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }
}
