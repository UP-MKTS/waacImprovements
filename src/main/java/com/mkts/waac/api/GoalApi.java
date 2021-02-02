package com.mkts.waac.api;

import com.mkts.waac.Dto.GoalDto;
import com.mkts.waac.services.GoalService;
import com.mkts.waac.services.utils.ErrorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "api/goal",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
public class GoalApi {


	private GoalService goalService;

	private ErrorDataService errorDataService;

	@Autowired
	public GoalApi(GoalService goalService, ErrorDataService errorDataService) {
		this.goalService = goalService;
		this.errorDataService = errorDataService;
	}

	/*@GetMapping("/{sorting}")
	public ResponseEntity<List<ScoreBean>> getAllScores(@PathVariable Boolean sorting) {
		List<ScoreBean> allScores = scoreService.getAllScore(sorting);
		ResponseEntity<List<ScoreBean>> response = new ResponseEntity<>(allScores, HttpStatus.OK);

		return response;
	}*/

	@PostMapping
	public ResponseEntity<Map<String, String>> addGoal(@Valid @RequestBody GoalDto goalDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = errorDataService.fillErrorMap(bindingResult);
			return new ResponseEntity<>(errorMap, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			goalService.save(goalDto);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@DeleteMapping(value = "/{goalId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<String> removeGoal(@PathVariable Integer goalId) {
		goalService.delete(goalId);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<GoalDto>> getAllGoal() {
		List<GoalDto> goalDtos = goalService.getAll();
		return new ResponseEntity<>(goalDtos, HttpStatus.OK);
	}

	@GetMapping(value = "/{goalId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<GoalDto> getGoal(@PathVariable Integer goalId) {
		GoalDto goalDto = goalService.getOne(goalId);
		return new ResponseEntity<>(goalDto, HttpStatus.OK);
	}
}
