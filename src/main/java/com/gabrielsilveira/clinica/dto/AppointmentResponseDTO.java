package com.gabrielsilveira.clinica.dto;

import com.gabrielsilveira.clinica.entities.Appointment;
import com.gabrielsilveira.clinica.entities.AppointmentStatus;

import java.time.Instant;

public class AppointmentResponseDTO {

    private Long id;
    private Instant moment;
    private AppointmentStatus status;
    private String patientName;
    private String doctorName;
    private String specialtyName;

    public AppointmentResponseDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.moment = appointment.getMoment();
        this.status = appointment.getStatus();
        this.patientName = appointment.getPatient().getName();
        this.doctorName = appointment.getDoctor().getName();
        this.specialtyName = appointment.getDoctor().getSpecialty().getName();
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }
}