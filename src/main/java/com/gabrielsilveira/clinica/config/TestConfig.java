package com.gabrielsilveira.clinica.config;

import com.gabrielsilveira.clinica.entities.Doctor;
import com.gabrielsilveira.clinica.entities.Patient;
import com.gabrielsilveira.clinica.entities.Specialty;
import com.gabrielsilveira.clinica.repositories.DoctorRepository;
import com.gabrielsilveira.clinica.repositories.PatientRepository;
import com.gabrielsilveira.clinica.repositories.SpecialtyRepository;
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

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public void run(String... args) throws Exception {
        Patient p1 = new Patient(null, "João Silva", "111.222.333-44", "joao@gmail.com", "11988880001", LocalDate.of(1990, 5, 15));
        Patient p2 = new Patient(null, "Fernanda Costa", "555.666.777-88", "fernanda@gmail.com", "11988880002", LocalDate.of(1985, 3, 22));
        patientRepository.saveAll(Arrays.asList(p1, p2));

        Specialty sp1 = new Specialty(null, "Cardiology", "Heart and cardiovascular system");
        Specialty sp2 = new Specialty(null, "Orthopedics", "Bones, joints and muscles");
        Specialty sp3 = new Specialty(null, "Dermatology", "Skin conditions");
        specialtyRepository.saveAll(Arrays.asList(sp1, sp2, sp3));

        Doctor d1 = new Doctor(null, "Dr. Carlos Lima", "CRM-SP 12345", "carlos@clinic.com", "11999990001", sp1);
        Doctor d2 = new Doctor(null, "Dr. Ana Souza", "CRM-SP 54321", "ana@clinic.com", "11999990002", sp2);
        Doctor d3 = new Doctor(null, "Dr. Beatriz Nunes", "CRM-SP 99999", "bia@clinic.com", "11999990003", sp3);
        doctorRepository.saveAll(Arrays.asList(d1, d2, d3));
    }
}
