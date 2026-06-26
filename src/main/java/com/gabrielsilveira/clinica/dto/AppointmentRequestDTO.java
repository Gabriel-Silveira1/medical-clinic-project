package com.gabrielsilveira.clinica.dto;

import com.gabrielsilveira.clinica.entities.AppointmentStatus;

import java.time.Instant;

public class AppointmentRequestDTO {

    private Instant moment;
    private AppointmentStatus status;
    private Long patientId;
    private Long doctorId;

    public AppointmentRequestDTO() {
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}