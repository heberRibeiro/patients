package me.salvus.patients.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import me.salvus.patients.domain.Patient;
import me.salvus.patients.repositories.PatientRepository;

@RestController
@RequestMapping("/api")
public class PatientResource {

	@Autowired
	private PatientRepository repo;

	@GetMapping(value = "/patients")
	public ResponseEntity<List<Patient>> findAll() {

		List<Patient> patients = repo.findAll();
		return ResponseEntity.ok().body(patients);

	}

	@PostMapping(value = "/patients")
	public ResponseEntity<Void> insert(@RequestBody Patient patient) {

		patient.setId(null);
		Patient obj = repo.save(patient);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

	@DeleteMapping(value = "patients/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		repo.deleteById(id);
		return ResponseEntity.noContent().build();

	}

}
