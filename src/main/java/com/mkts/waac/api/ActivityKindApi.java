package com.mkts.waac.api;

import com.mkts.waac.Dto.ActivityKindDto;
import com.mkts.waac.services.ActivityKindService;
import com.mkts.waac.services.utils.ErrorDataService;
import com.mkts.waac.services.utils.ReportManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;


@RestController
@RequestMapping(value = "api/activity-kind",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
public class ActivityKindApi {


	private ActivityKindService activityKindService;

	private ErrorDataService errorDataService;

	@Autowired
	private ReportManager reportManager;

	@Autowired
	public ActivityKindApi(ActivityKindService activityKindService, ErrorDataService errorDataService) {
		this.activityKindService = activityKindService;
		this.errorDataService = errorDataService;
	}

	/*@GetMapping("/{sorting}")
	public ResponseEntity<List<ScoreBean>> getAllScores(@PathVariable Boolean sorting) {
		List<ScoreBean> allScores = scoreService.getAllScore(sorting);
		ResponseEntity<List<ScoreBean>> response = new ResponseEntity<>(allScores, HttpStatus.OK);

		return response;
	}*/

	@PostMapping
	public ResponseEntity<Map<String, String>> addActivityKind(@Valid @RequestBody ActivityKindDto activityKindDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = errorDataService.fillErrorMap(bindingResult);
			return new ResponseEntity<>(errorMap, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			activityKindService.save(activityKindDto);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@GetMapping( value ="/download",consumes = MediaType.ALL_VALUE )
	public void myExcel(HttpServletResponse response)
			throws IOException, ParseException {

		String reportFile = activityKindService.toExcel();

		byte[] reportContent = reportManager.downloadReport(reportFile);
		try {
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment; filename*=UTF-8''" + "test" + ".xls");
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(reportContent);
		} catch (IOException e) {
			throw new RuntimeException("Ошибка загрузки файла.");
		}
		reportManager.removeReport(reportFile);
	}

	@DeleteMapping(value = "/{activityKindId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<String> removeActivityKind(@PathVariable Integer activityKindId) {
		activityKindService.delete(activityKindId);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@GetMapping(value = "/{activityKindId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ActivityKindDto> getActivityKind(@PathVariable Integer activityKindId) {
		ActivityKindDto activityKindDto = activityKindService.getOne(activityKindId);
		return new ResponseEntity<>(activityKindDto, HttpStatus.OK);
	}
}
