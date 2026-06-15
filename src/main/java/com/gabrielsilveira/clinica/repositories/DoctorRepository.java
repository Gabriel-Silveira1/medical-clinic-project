package com.gabrielsilveira.clinica.repositories;

import com.gabrielsilveira.clinica.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
