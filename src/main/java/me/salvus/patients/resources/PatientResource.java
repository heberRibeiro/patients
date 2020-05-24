package me.salvus.patients.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import me.salvus.patients.domain.Patient;
import me.salvus.patients.services.PatientService;

@RestController
@RequestMapping("/api")
public class PatientResource {

	@Autowired
	private PatientService service;

	@GetMapping(value = "/patients")
	public ResponseEntity<List<Patient>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "5") int limit,
			@RequestParam(value = "attribute", defaultValue = "id") String attribute,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, attribute));

		List<Patient> patients = service.findAll(pageable);
		return ResponseEntity.ok().body(patients);

	}

	@GetMapping(value = "/patients/{id}")
	public ResponseEntity<Patient> findById(@PathVariable Integer id) {

		Patient patient = service.findById(id);
		return ResponseEntity.ok().body(patient);

	}

	@PostMapping(value = "/patients")
	public ResponseEntity<Void> insert(@RequestBody Patient patient) {

		patient.setId(null);
		Patient obj = service.insert(patient);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

	@PutMapping(value = "/patients/{id}")
	public ResponseEntity<Patient> update(@RequestBody Patient patient, @PathVariable Integer id) {

		Patient obj = service.update(patient, id);
		return ResponseEntity.ok().body(obj);
	}

	@DeleteMapping(value = "patients/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
