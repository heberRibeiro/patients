package me.salvus.patients;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import me.salvus.patients.domain.Patient;
import me.salvus.patients.repositories.PatientRepository;

@SpringBootApplication
public class PatientsApplication implements CommandLineRunner {
	
	@Autowired
	private PatientRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(PatientsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Patient pac01 = new Patient(null, "José", "(81)9999999", "address", "Recife", "PE");
		Patient pac02 = new Patient(null, "Maria", "(81)9999999", "address", "Recife", "PE");
		Patient pac03 = new Patient(null, "Benedito", "(81)9999999", "address", "Recife", "PE");
		Patient pac04 = new Patient(null, "Ana", "(81)9999999", "address", "Recife", "PE");
		
		repo.saveAll(Arrays.asList(pac01, pac02, pac03, pac04));
		
	}

}
