package com.mkts.waac.api;

import com.mkts.waac.Dto.AccompPasspSaveDto;
import com.mkts.waac.Dto.WasteTypeDto;
import com.mkts.waac.services.WasteTypeService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "api/waste-type",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
public class WasteTypeApi {


	private WasteTypeService wasteTypeService;

	private ErrorDataService errorDataService;

	@Autowired
	private ReportManager reportManager;

	@Autowired
	public WasteTypeApi(WasteTypeService wasteTypeService, ErrorDataService errorDataService) {
		this.wasteTypeService = wasteTypeService;
		this.errorDataService = errorDataService;
	}

	@GetMapping( value ="/download",consumes = MediaType.ALL_VALUE )
	public void myExcel(HttpServletResponse response)
			throws IOException, ParseException {

		String reportFile = wasteTypeService.toExcel();

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


	/*@GetMapping("/{sorting}")
	public ResponseEntity<List<ScoreBean>> getAllScores(@PathVariable Boolean sorting) {
		List<ScoreBean> allScores = scoreService.getAllScore(sorting);
		ResponseEntity<List<ScoreBean>> response = new ResponseEntity<>(allScores, HttpStatus.OK);

		return response;
	}*/

	@PostMapping
	public ResponseEntity<Map<String, String>> addWasteType(@Valid @RequestBody WasteTypeDto wasteTypeDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = errorDataService.fillErrorMap(bindingResult);
			return new ResponseEntity<>(errorMap, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			wasteTypeService.save(wasteTypeDto);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@DeleteMapping(value = "/{wasteTypeId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<String> removeWasteType(@PathVariable Integer wasteTypeId) {
		wasteTypeService.delete(wasteTypeId);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}


	@GetMapping(value = "/{wasteTypeId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<WasteTypeDto> getWasteType(@PathVariable Integer wasteTypeId) {
		WasteTypeDto wasteTypeDto = wasteTypeService.getOne(wasteTypeId);
		return new ResponseEntity<>(wasteTypeDto, HttpStatus.OK);
	}


	@GetMapping(value = "getAllWastes/{getBylistId}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<List<WasteTypeDto>> getWasteTypes(@PathVariable(name = "getBylistId") String indexes) {

		String[] split = indexes.split("-");
		List<Integer> ind = new ArrayList<>();
		for (String temp : split)
		{
			ind.add(Integer.parseInt(temp));
		}
		return new ResponseEntity<>(wasteTypeService.getAllByListId(ind), HttpStatus.OK);
	}
}
