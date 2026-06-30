package com.gabrielsilveira.clinica.dto;

import com.gabrielsilveira.clinica.entities.Payment;

import java.time.Instant;

public class PaymentResponseDTO {

    private Long id;
    private Instant moment;
    private Double amount;
    private String patientName;
    private String doctorName;
    private String diagnosis;

    public PaymentResponseDTO(Payment payment) {
        this.id = payment.getId();
        this.moment = payment.getMoment();
        this.amount = payment.getAmount();
        this.patientName = payment.getConsultation().getAppointment().getPatient().getName();
        this.doctorName = payment.getConsultation().getAppointment().getDoctor().getName();
        this.diagnosis = payment.getConsultation().getDiagnosis();
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public Double getAmount() {
        return amount;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }
}