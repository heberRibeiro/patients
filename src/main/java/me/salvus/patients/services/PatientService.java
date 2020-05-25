package me.salvus.patients.services;

import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import me.salvus.patients.domain.Patient;
import me.salvus.patients.repositories.PatientRepository;
import me.salvus.patients.services.exceptions.ObjectNotFoundException;

@Service
public class PatientService {

	@Autowired
	private PatientRepository repository;

	public List<Patient> findAll(Pageable pageable) {
		return repository.findAll(pageable).getContent();
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

	public Patient update(Patient patient, Integer id) {

		try {
			Patient obj = repository.getOne(id); // return a reference to the entity
			obj.setName(patient.getName());
			obj.setGender(patient.getGender());
			obj.setBirth(patient.getBirth());
			obj.setPhone(patient.getPhone());
			obj.setAddress(patient.getAddress());
			obj.setCity(patient.getCity());
			obj.setState(patient.getState());

			return repository.save(obj);

		} catch (EntityNotFoundException e) {
			throw new ObjectNotFoundException("Patient not found in data base to be updated.");
		}

	}

	public void delete(Integer id) {
		try {
			repository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException("Patient not found in data base.");
		}
	}

}
