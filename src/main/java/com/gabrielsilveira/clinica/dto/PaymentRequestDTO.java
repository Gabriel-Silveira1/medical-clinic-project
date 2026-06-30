package com.gabrielsilveira.clinica.dto;

import java.time.Instant;

public class PaymentRequestDTO {

    private Instant moment;
    private Double amount;
    private Long consultationId;

    public PaymentRequestDTO() {
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }
}