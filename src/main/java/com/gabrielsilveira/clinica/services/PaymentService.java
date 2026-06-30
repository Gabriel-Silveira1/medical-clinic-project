package com.gabrielsilveira.clinica.services;

import com.gabrielsilveira.clinica.dto.PaymentRequestDTO;
import com.gabrielsilveira.clinica.dto.PaymentResponseDTO;
import com.gabrielsilveira.clinica.entities.Consultation;
import com.gabrielsilveira.clinica.entities.Payment;
import com.gabrielsilveira.clinica.repositories.ConsultationRepository;
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

    @Autowired
    private ConsultationRepository consultationRepository;

    public List<PaymentResponseDTO> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(PaymentResponseDTO::new)
                .toList();
    }

    public PaymentResponseDTO findById(Long id) {
        Optional<Payment> obj = paymentRepository.findById(id);
        Payment payment = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return new PaymentResponseDTO(payment);
    }

    public PaymentResponseDTO insert(PaymentRequestDTO dto) {
        Payment payment = new Payment();
        payment.setMoment(dto.getMoment());
        payment.setAmount(dto.getAmount());
        Consultation consultation = consultationRepository.getReferenceById(dto.getConsultationId());
        payment.setConsultation(consultation);
        payment = paymentRepository.save(payment);
        return new PaymentResponseDTO(payment);
    }

    public PaymentResponseDTO update(Long id, PaymentRequestDTO dto) {
        try {
            Payment payment = paymentRepository.getReferenceById(id);
            payment.setMoment(dto.getMoment());
            payment.setAmount(dto.getAmount());
            return new PaymentResponseDTO(paymentRepository.save(payment));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public void delete(Long id) {
        try {
            paymentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}