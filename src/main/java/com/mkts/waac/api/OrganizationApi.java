package com.mkts.waac.api;

import com.mkts.waac.Dto.OrganizationDto;
import com.mkts.waac.services.OrganizationService;
import com.mkts.waac.services.utils.ErrorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping(value = "api/organization",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationApi {


	private OrganizationService organizationService;

	private ErrorDataService errorDataService;

	@Autowired
	public OrganizationApi(OrganizationService organizationService, ErrorDataService errorDataService) {
		this.organizationService = organizationService;
		this.errorDataService = errorDataService;
	}

	/*@GetMapping("/{sorting}")
	public ResponseEntity<List<ScoreBean>> getAllScores(@PathVariable Boolean sorting) {
		List<ScoreBean> allScores = scoreService.getAllScore(sorting);
		ResponseEntity<List<ScoreBean>> response = new ResponseEntity<>(allScores, HttpStatus.OK);

		return response;
	}*/

	@PostMapping
	public ResponseEntity<Map<String, String>> addOrganization(@Valid @RequestBody OrganizationDto organizationDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = errorDataService.fillErrorMap(bindingResult);
			return new ResponseEntity<>(errorMap, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			organizationService.save(organizationDto);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@DeleteMapping(value = "/{organizationId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<String> removeOrganization(@PathVariable Integer organizationId) {
		organizationService.delete(organizationId);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@GetMapping(value = "/{organizationId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<OrganizationDto> getOrganization(@PathVariable Integer organizationId) {
		OrganizationDto organizationDto = organizationService.getOne(organizationId);
		return new ResponseEntity<>(organizationDto, HttpStatus.OK);
	}
}
