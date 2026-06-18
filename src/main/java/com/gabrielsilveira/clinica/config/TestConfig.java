package com.gabrielsilveira.clinica.config;

import com.gabrielsilveira.clinica.entities.*;
import com.gabrielsilveira.clinica.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
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

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private PaymentRepository paymentRepository;

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

        Appointment a1 = new Appointment(null, Instant.parse("2024-03-10T09:00:00Z"), AppointmentStatus.COMPLETED, p1, d1);
        Appointment a2 = new Appointment(null, Instant.parse("2024-03-11T14:00:00Z"), AppointmentStatus.SCHEDULED, p2, d2);
        Appointment a3 = new Appointment(null, Instant.parse("2024-03-12T10:30:00Z"), AppointmentStatus.CONFIRMED, p1, d3);
        appointmentRepository.saveAll(Arrays.asList(a1, a2, a3));

        Consultation c1 = new Consultation(null, "Hypertension stage 1", "Losartan 50mg", a1);
        consultationRepository.save(c1);

        Payment pay1 = new Payment(null, Instant.parse("2024-03-10T10:00:00Z"), 250.00, c1);
        paymentRepository.save(pay1);
    }
}
