package com.mkts.waac.api;

import com.mkts.waac.Dto.DangerousClassDto;
import com.mkts.waac.services.DangerousClassService;
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
@RequestMapping(value = "api/dangerous-class",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
public class DangerousClassApi {


	private DangerousClassService dangerousClassService;

	@Autowired
	private ReportManager reportManager;

	private ErrorDataService errorDataService;

	@Autowired
	public DangerousClassApi(DangerousClassService dangerousClassService, ErrorDataService errorDataService) {
		this.dangerousClassService = dangerousClassService;
		this.errorDataService = errorDataService;
	}

	@GetMapping( value ="/download",consumes = MediaType.ALL_VALUE )
	public void myExcel(HttpServletResponse response)
			throws IOException, ParseException {

		String reportFile = dangerousClassService.toExcel();

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

	@PostMapping
	public ResponseEntity<Map<String, String>> addDangerousClass(@Valid @RequestBody DangerousClassDto dangerousClassDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = errorDataService.fillErrorMap(bindingResult);
			return new ResponseEntity<>(errorMap, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			dangerousClassService.save(dangerousClassDto);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@DeleteMapping(value = "/{dangerousClassId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<String> removeDangerousClass(@PathVariable Integer dangerousClassId) {
		dangerousClassService.delete(dangerousClassId);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@GetMapping(value = "/{dangerousClassId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<DangerousClassDto> getDangerousClass(@PathVariable Integer dangerousClassId) {
		DangerousClassDto dangerousClassDto = dangerousClassService.getOne(dangerousClassId);
		return new ResponseEntity<>(dangerousClassDto, HttpStatus.OK);
	}
}
