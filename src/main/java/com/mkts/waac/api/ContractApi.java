package com.mkts.waac.api;

import com.mkts.waac.Dto.ContractDto;
import com.mkts.waac.services.ContractService;
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
@RequestMapping(value = "api/contract",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
public class ContractApi {


	private ContractService contractService;

	private ErrorDataService errorDataService;

	@Autowired
	public ContractApi(ContractService contractService, ErrorDataService errorDataService) {
		this.contractService = contractService;
		this.errorDataService = errorDataService;
	}

	/*@GetMapping("/{sorting}")
	public ResponseEntity<List<ScoreBean>> getAllScores(@PathVariable Boolean sorting) {
		List<ScoreBean> allScores = scoreService.getAllScore(sorting);
		ResponseEntity<List<ScoreBean>> response = new ResponseEntity<>(allScores, HttpStatus.OK);

		return response;
	}*/

	@PostMapping
	public ResponseEntity<Map<String, String>> addContract(@Valid @RequestBody ContractDto contractDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = errorDataService.fillErrorMap(bindingResult);
			return new ResponseEntity<>(errorMap, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			contractService.save(contractDto);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@DeleteMapping(value = "/{contractId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<String> removeContract(@PathVariable Integer contractId) {
		contractService.delete(contractId);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@GetMapping(value = "/{contractId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ContractDto> getContract(@PathVariable Integer contractId) {
		ContractDto contractDto = contractService.getOne(contractId);
		return new ResponseEntity<>(contractDto, HttpStatus.OK);
	}
}
