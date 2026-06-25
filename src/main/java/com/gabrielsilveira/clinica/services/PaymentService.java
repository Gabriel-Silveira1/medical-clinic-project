package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.entities.Payment;
import com.gabrielsilveira.clinica.repositories.PaymentRepository;
import com.gabrielsilveira.clinica.services.exceptions.DatabaseException;
import com.gabrielsilveira.clinica.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Payment findById(Long id) {
        Optional<Payment> obj = paymentRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Payment insert(Payment obj) {
        return paymentRepository.save(obj);
    }

    public void delete (Long id) {
        try {
            paymentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Payment update(Long id, Payment obj) {
        try {
            Payment payment = paymentRepository.getReferenceById(id);
            updateData(payment, obj);
            return paymentRepository.save(payment);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Payment payment, Payment obj) {
        payment.setMoment(obj.getMoment());
        payment.setAmount(obj.getAmount());
    }
}
