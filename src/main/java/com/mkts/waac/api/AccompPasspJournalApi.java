package com.mkts.waac.api;

import com.mkts.waac.Dao.AccompPasspDao;
import com.mkts.waac.Dao.AccompPasspWasteDao;
import com.mkts.waac.Dao.WasteTypeDao;
import com.mkts.waac.Dto.AccompPasspDetailsDto;
import com.mkts.waac.Dto.AccompPasspDto;
import com.mkts.waac.Dto.AccompPasspJournalDto;
import com.mkts.waac.Dto.AccompPasspWasteDto;
import com.mkts.waac.models.AccompPasspWaste;
import com.mkts.waac.models.WasteType;
//import com.mkts.waac.services.AccompPasspReportService;
import com.mkts.waac.services.AccompPasspReportService;
import com.mkts.waac.services.AccompPasspService;
//import com.mkts.waac.services.helpers.HelpJournalDto;
import com.mkts.waac.services.utils.ErrorDataService;
//import com.mkts.waac.services.utils.ReportManager;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "api/accomp-passp-journal",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
public class AccompPasspJournalApi {


	private AccompPasspService accompPasspService;

	private ErrorDataService errorDataService;

	@Autowired
	private AccompPasspReportService accompPasspReportService;

	@Autowired
	private ReportManager reportManager;

	@Autowired
	private AccompPasspWasteDao accompPasspWasteDao;

	@Autowired
	private AccompPasspDao accompPasspDao;

	@Autowired
	private WasteTypeDao wasteTypeDao;

	@Autowired
	public AccompPasspJournalApi(AccompPasspService accompPasspService, ErrorDataService errorDataService) {
		this.accompPasspService = accompPasspService;
		this.errorDataService = errorDataService;
	}

	/*@GetMapping("/{sorting}")
	public ResponseEntity<List<ScoreBean>> getAllScores(@PathVariable Boolean sorting) {
		List<ScoreBean> allScores = scoreService.getAllScore(sorting);
		ResponseEntity<List<ScoreBean>> response = new ResponseEntity<>(allScores, HttpStatus.OK);

		return response;
	}*/

	/*@PostMapping
	public ResponseEntity<Map<String, String>> addAccompPassp(@Valid @RequestBody AccompPasspDto accompPasspDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = errorDataService.fillErrorMap(bindingResult);
			return new ResponseEntity<>(errorMap, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			accompPasspService.save(accompPasspDto);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}*/

	/*@DeleteMapping(value = "/{accompPasspId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<String> removeAccompPassp(@PathVariable Integer accompPasspId) {
		accompPasspService.delete(accompPasspId);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}*/

	@GetMapping(value = "/{year}/{month}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<List<AccompPasspJournalDto>> getAllAccompPasspByYearAndMonth(@PathVariable String year, @PathVariable String month) {
		List<AccompPasspJournalDto> accompPasspJournalDtos = accompPasspService.getAllByYearAndMonth(year, month);
		return new ResponseEntity<>(accompPasspJournalDtos, HttpStatus.OK);
	}

	@GetMapping( value ="/download/{year}",consumes = MediaType.ALL_VALUE )
	public void myExcel(@PathVariable Integer year,HttpServletResponse response)
			throws IOException, ParseException {

		String reportFile = accompPasspReportService.createAccompPasspJournalReport(year);

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

	/*@GetMapping(value = "/details/{accompPasspId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<AccompPasspDetailsDto> getAccompPasspDetails(@PathVariable Integer accompPasspId) {
		AccompPasspDetailsDto accompPasspDetailsDto = accompPasspService.getDetailsById(accompPasspId);
		return new ResponseEntity<>(accompPasspDetailsDto, HttpStatus.OK);
	}*/

	/*@PutMapping(value = "/{accompPasspId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<String> duplicateAccompPassp(@PathVariable Integer accompPasspId) {
		AccompPasspDto accompPasspDto = accompPasspService.getOne(accompPasspId);
		accompPasspDto = accompPasspService.setNullParameters(accompPasspDto);
		accompPasspService.save(accompPasspDto);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@GetMapping(value = "/next-number",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Integer> getNextNumber() {
		Integer nextNumber = accompPasspService.getNextNumber();
		return new ResponseEntity<>(nextNumber, HttpStatus.OK);
	}*/
}
