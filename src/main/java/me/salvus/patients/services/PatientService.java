package me.salvus.patients.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import me.salvus.patients.domain.Patient;
import me.salvus.patients.repositories.PatientRepository;
import me.salvus.patients.services.exceptions.ObjectNotFoundException;

@Service
public class PatientService {

	@Autowired
	private PatientRepository repository;

	public List<Patient> findAll() {
		return repository.findAll();
	}

	
	public Patient findById(Integer id) {
		
		try {
			Patient patient = repository.findById(id).get();
			return patient;

		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException("Patient not found in data base.");
		}
	}

	public Patient insert(Patient patient) {
		return repository.save(patient);
	}

	public void delete(Integer id) {
		try {
			repository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException("Patient not found in data base.");
		}
	}

}
