package com.gabrielsilveira.clinica.dto;

import com.gabrielsilveira.clinica.entities.Specialty;

public class SpecialtyResponseDTO {

    private Long id;
    private String name;
    private String description;

    public SpecialtyResponseDTO(Specialty specialty) {
        this.id = specialty.getId();
        this.name = specialty.getName();
        this.description = specialty.getDescription();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}