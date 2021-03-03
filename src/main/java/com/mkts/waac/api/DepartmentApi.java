package com.mkts.waac.api;

import com.mkts.waac.security.dto.SignedInAccountDto;
import com.mkts.waac.security.services.SecurityService;
import com.mkts.waac.services.utils.ErrorDataService;
import com.mkts.waac.Dto.DepartmentDto;
import com.mkts.waac.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "api/department",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentApi {


	private DepartmentService departmentService;

	private ErrorDataService errorDataService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	public DepartmentApi(DepartmentService departmentService, ErrorDataService errorDataService) {
		this.departmentService = departmentService;
		this.errorDataService = errorDataService;
	}

	/*@GetMapping("/{sorting}")
	public ResponseEntity<List<ScoreBean>> getAllScores(@PathVariable Boolean sorting) {
		List<ScoreBean> allScores = scoreService.getAllScore(sorting);
		ResponseEntity<List<ScoreBean>> response = new ResponseEntity<>(allScores, HttpStatus.OK);

		return response;
	}*/

	@PostMapping
	public ResponseEntity<Map<String, String>> addDepartment(@Valid @RequestBody DepartmentDto departmentDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = errorDataService.fillErrorMap(bindingResult);
			return new ResponseEntity<>(errorMap, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			departmentService.save(departmentDto);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@DeleteMapping(value = "/{departmentId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<String> removeDepartment(@PathVariable Integer departmentId) {
		departmentService.delete(departmentId);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@GetMapping(value = "/{departmentId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<DepartmentDto> getUser(@PathVariable Integer departmentId) {
		DepartmentDto departmentDto = departmentService.getOne(departmentId);

		return new ResponseEntity<>(departmentDto, HttpStatus.OK);
	}

	@GetMapping(consumes = MediaType.ALL_VALUE)
	public ResponseEntity<List<DepartmentDto>> getDepartments() {
		SignedInAccountDto signedInAccount = securityService.getSignedInAccount();
		GrantedAuthority primaryAuthority = signedInAccount.getPrimaryAuthority();
		String userRole = primaryAuthority.getAuthority();

		List<DepartmentDto> departmentDtos = new ArrayList<>();
		Integer departmentId = signedInAccount.getDepartmentId();
		if (userRole.equals("admin")||userRole.equals("supervisor")||userRole.equals("reader")) {
			departmentDtos = departmentService.getAll("shortName");
			departmentId = departmentDtos.get(0).getId();
		} else {
			departmentDtos.add(departmentService.getOne(departmentId));
		}

		return new ResponseEntity<>(departmentDtos, HttpStatus.OK);
	}
}
