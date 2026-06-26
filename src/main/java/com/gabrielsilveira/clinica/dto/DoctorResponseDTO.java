package com.gabrielsilveira.clinica.dto;

import com.gabrielsilveira.clinica.entities.Doctor;

public class DoctorResponseDTO {
    private Long id;
    private String name;
    private String crm;
    private String email;
    private String phone;

    private String specialtyName;

    public  DoctorResponseDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.crm = doctor.getCrm();
        this.email = doctor.getEmail();
        this.phone = doctor.getPhone();
        this.specialtyName = doctor.getSpecialty().getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCrm() {
        return crm;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }
}
