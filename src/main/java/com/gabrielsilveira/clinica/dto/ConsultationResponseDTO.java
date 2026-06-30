package com.gabrielsilveira.clinica.dto;

import com.gabrielsilveira.clinica.entities.Consultation;

public class ConsultationResponseDTO {
    private Long id;
    private String diagnosis;
    private String prescription;
    private String patientName;
    private String doctorName;

    public ConsultationResponseDTO(Consultation consultation) {
        this.id = consultation.getId();
        this.diagnosis = consultation.getDiagnosis();
        this.prescription = consultation.getPrescription();
        this.patientName = consultation.getAppointment().getPatient().getName();
        this.doctorName = consultation.getAppointment().getDoctor().getName();
    }

    public Long getId() {
        return id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }
}
