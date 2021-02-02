package com.mkts.waac.api;

import com.mkts.waac.Dto.PersonDto;
import com.mkts.waac.services.PersonService;
import com.mkts.waac.services.utils.ErrorDataService;
import com.mkts.waac.Dto.DepartmentDto;
import com.mkts.waac.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping(value = "api/person",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonApi {


	private PersonService personService;

	private ErrorDataService errorDataService;

	@Autowired
	public PersonApi(PersonService personService, ErrorDataService errorDataService) {
		this.personService = personService;
		this.errorDataService = errorDataService;
	}

	/*@GetMapping("/{sorting}")
	public ResponseEntity<List<ScoreBean>> getAllScores(@PathVariable Boolean sorting) {
		List<ScoreBean> allScores = scoreService.getAllScore(sorting);
		ResponseEntity<List<ScoreBean>> response = new ResponseEntity<>(allScores, HttpStatus.OK);

		return response;
	}*/

	@PostMapping
	public ResponseEntity<Map<String, String>> addPerson(@Valid @RequestBody PersonDto personDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = errorDataService.fillErrorMap(bindingResult);
			return new ResponseEntity<>(errorMap, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			personService.save(personDto);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@DeleteMapping(value = "/{personId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<String> removePerson(@PathVariable Integer personId) {
		personService.delete(personId);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@GetMapping(value = "/{personId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<PersonDto> getPerson(@PathVariable Integer personId) {
		PersonDto personDto = personService.getOne(personId);
		return new ResponseEntity<>(personDto, HttpStatus.OK);
	}
}
