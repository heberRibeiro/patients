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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.salvus.patients.domain.Patient;
import me.salvus.patients.services.PatientService;

// Swagger customization @Api(), @ApiOperation and @ApiOperation
@Api(value = "Patients Endpoint", description = "Application endpoints", tags = { "Patients Endpoint" })
@RestController
@RequestMapping("/api")
public class PatientResource {

	@Autowired
	private PatientService service;

	@ApiOperation(value = "Finds all patients passing the appropriate parameters")
	@GetMapping(value = "/patients")
	public ResponseEntity<List<Patient>> findAll(
			@ApiParam(value = "Indicates the page number fetched starting at zero") @RequestParam(value = "page", defaultValue = "0") int page,
			@ApiParam(value = "Indicates the number of patients searched per page") @RequestParam(value = "limit", defaultValue = "5") int limit,
			@ApiParam(value = "Indicates the attribute that will be used to order the search") @RequestParam(value = "attribute", defaultValue = "id") String attribute,
			@ApiParam(value = "Defines whether the ordering will be increasing or decreasing") @RequestParam(value = "direction", defaultValue = "asc") String direction) {

		Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, attribute));

		List<Patient> patients = service.findAll(pageable);
		return ResponseEntity.ok().body(patients);

	}

	@ApiOperation(value = "Finds the patient according to the id")
	@GetMapping(value = "/patients/{id}")
	public ResponseEntity<Patient> findById(@ApiParam(value = "Patient id for search") @PathVariable Integer id) {

		Patient patient = service.findById(id);
		return ResponseEntity.ok().body(patient);

	}

	@ApiOperation(value = "Inserts patient passing json through the request body")
	@PostMapping(value = "/patients")
	public ResponseEntity<Void> insert(
			@ApiParam(value = "Json that will come in the body of the request with information of the patient to be inserted") @RequestBody Patient patient) {

		patient.setId(null);
		Patient obj = service.insert(patient);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

	@ApiOperation(value = "Update the patient according to the id and json through the request body")
	@PutMapping(value = "/patients/{id}")
	public ResponseEntity<Patient> update(
			@ApiParam(value = "Json that will come in the body of the request with information of the patient to be updated") @RequestBody Patient patient,
			@ApiParam(value = "Patient id to be changed") @PathVariable Integer id) {

		Patient obj = service.update(patient, id);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Delete the patient according to the id") // Swagger customization
	@DeleteMapping(value = "patients/{id}")
	public ResponseEntity<Void> delete(@ApiParam(value = "Patient id to be deleted") @PathVariable Integer id) {

		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
