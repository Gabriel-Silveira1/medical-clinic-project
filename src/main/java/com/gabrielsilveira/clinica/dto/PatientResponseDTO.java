package com.gabrielsilveira.clinica.dto;

import com.gabrielsilveira.clinica.entities.Patient;

import java.time.LocalDate;

public class PatientResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;

    public PatientResponseDTO(Patient patient) {
        this.id = patient.getId();
        this.name = patient.getName();
        this.email = patient.getEmail();
        this.phone = patient.getPhone();
        this.birthDate = patient.getBirthDate();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}
