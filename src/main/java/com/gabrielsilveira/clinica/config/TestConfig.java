package com.gabrielsilveira.clinica.config;

import com.gabrielsilveira.clinica.entities.Patient;
import com.gabrielsilveira.clinica.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void run(String... args) throws Exception {
        Patient p1 = new Patient(null, "João Silva", "111.222.333-44", "joao@gmail.com", "11988880001", LocalDate.of(1990, 5, 15));
        Patient p2 = new Patient(null, "Fernanda Costa", "555.666.777-88", "fernanda@gmail.com", "11988880002", LocalDate.of(1985, 3, 22));
        patientRepository.saveAll(Arrays.asList(p1, p2));
    }
}
