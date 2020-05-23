package me.salvus.patients.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.salvus.patients.domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
