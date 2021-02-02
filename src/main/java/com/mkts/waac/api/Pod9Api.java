package com.mkts.waac.api;

import com.mkts.waac.Dao.DepartmentDao;
import com.mkts.waac.Dto.*;
import com.mkts.waac.mappers.Pod9Mapper;
import com.mkts.waac.mappers.WasteTypeMapper;
import com.mkts.waac.models.AccompPasspDepartment;
import com.mkts.waac.models.Department;
import com.mkts.waac.models.Pod9OwnWaste;
import com.mkts.waac.models.WasteType;
import com.mkts.waac.services.DepartmentService;
import com.mkts.waac.services.Pod9OwnWasteService;
import com.mkts.waac.services.Pod9ReportService;
import com.mkts.waac.services.Pod9Service;
import com.mkts.waac.services.helpers.Pod9Helper;
import com.mkts.waac.services.utils.CalcCountStoredService;
import com.mkts.waac.services.utils.ErrorDataService;
import com.mkts.waac.services.utils.ReportManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Predicate;
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
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.cglib.core.CollectionUtils.filter;


@RestController
@RequestMapping(value = "api/pod9",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
public class Pod9Api {



	private Pod9OwnWasteService pod9OwnWasteService;

	private ErrorDataService errorDataService;

	private CalcCountStoredService calcCountStoredService;

	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private Pod9OwnWasteService pod9ReportService;

	@Autowired
	private ReportManager reportManager;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private WasteTypeMapper wasteTypeMapper;

	@Autowired
	private Pod9Mapper pod9Mapper;

	@Autowired
	public Pod9Api(Pod9OwnWasteService pod9OwnWasteService, ErrorDataService errorDataService, CalcCountStoredService calcCountStoredService) {
		this.pod9OwnWasteService = pod9OwnWasteService;
		this.errorDataService = errorDataService;
		this.calcCountStoredService = calcCountStoredService;
	}

	/*@GetMapping("/{sorting}")
	public ResponseEntity<List<ScoreBean>> getAllScores(@PathVariable Boolean sorting) {
		List<ScoreBean> allScores = scoreService.getAllScore(sorting);
		ResponseEntity<List<ScoreBean>> response = new ResponseEntity<>(allScores, HttpStatus.OK);

		return response;
	}*/

//	@PostMapping
//	public ResponseEntity<Map<String, String>> addPod9OwnWaste(@Valid @RequestBody Pod9OwnWasteDto pod9OwnWasteDto, BindingResult bindingResult) {
//		if (bindingResult.hasErrors()) {
//			Map<String, String> errorMap = errorDataService.fillErrorMap(bindingResult);
//			return new ResponseEntity<>(errorMap, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
//		} else {
//			pod9OwnWasteService.save(pod9OwnWasteDto);
//			calcCountStoredService.recalcCountStored(pod9OwnWasteDto.getWasteTypeId(), pod9OwnWasteDto.getDepartmentId());
//			return new ResponseEntity<>(null, HttpStatus.OK);
//		}
//	}
//
//	@DeleteMapping(value = "/{pod9OwnWasteId}",
//			consumes = MediaType.ALL_VALUE)
//	public ResponseEntity<String> removePod9OwnWaste(@PathVariable Integer pod9OwnWasteId) {
//		Pod9OwnWasteDto pod9OwnWasteDto = pod9OwnWasteService.getOne(pod9OwnWasteId);
//		Integer wasteTypeId = pod9OwnWasteDto.getWasteTypeId();
//		Integer departmentId = pod9OwnWasteDto.getDepartmentId();
//		pod9OwnWasteService.delete(pod9OwnWasteId);
//		calcCountStoredService.recalcCountStored(wasteTypeId, departmentId);
//		return new ResponseEntity<>("Success", HttpStatus.OK);
//	}
//
//	@PutMapping(value = "/{pod9OwnWasteId}",
//			consumes = MediaType.ALL_VALUE)
//	public ResponseEntity<String> duplicateAccompPassp(@PathVariable Integer pod9OwnWasteId) {
//		Pod9OwnWasteDto pod9OwnWasteDto = pod9OwnWasteService.getOne(pod9OwnWasteId);
//		pod9OwnWasteDto.setId(null);
//		pod9OwnWasteService.save(pod9OwnWasteDto);
//		calcCountStoredService.recalcCountStored(pod9OwnWasteDto.getWasteTypeId(), pod9OwnWasteDto.getDepartmentId());
//		return new ResponseEntity<>("Success", HttpStatus.OK);
//	}
//
//	@GetMapping(value = "/{pod9OwnWasteId}",
//			consumes = MediaType.ALL_VALUE)
//	public ResponseEntity<Pod9OwnWasteDto> getPod9OwnWaste(@PathVariable Integer pod9OwnWasteId) {
//		Pod9OwnWasteDto pod9OwnWasteDto = pod9OwnWasteService.getOne(pod9OwnWasteId);
//		return new ResponseEntity<>(pod9OwnWasteDto, HttpStatus.OK);
//	}


	@DeleteMapping(value = "/{pod9Id}",
			consumes = MediaType.ALL_VALUE)
	public void delete(@PathVariable Integer pod9Id){

		Pod9OwnWaste one = pod9OwnWasteService.getOne(pod9Id);
		List<Integer> wastes = new ArrayList<>();
		List<Integer> departments = new ArrayList<>();
		wastes.add(one.getWasteType().getId());
		departments.add(one.getDepartment().getId());
		String date = pod9OwnWasteService.toDto(one).getTransparentDate();
		pod9OwnWasteService.delete(pod9Id);
		calcCountStoredService.recalcCountStored(wastes,departments,date);
	}


	@GetMapping(value = "/change-department/{departmentId}/{yearMonth}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Pod9Helper> getAccompPasspByDepartmentId(@PathVariable Integer departmentId, @PathVariable YearMonth yearMonth) {

		DepartmentDto departmentFind = departmentService.getOne(departmentId);
		String month = String.valueOf(yearMonth.getMonthValue());
		String year = String.valueOf(yearMonth.getYear());
		month = month.length() < 2 ? "0" + month : month;
		List<Pod9OwnWaste> pod9Dto = pod9OwnWasteService.getAllByDate(month,year);

		Predicate pod9Department = new Predicate() {
			@Override
			public boolean evaluate(Object o) {
				Pod9OwnWaste pod = (Pod9OwnWaste) o;

				if(pod.getAccompPasspWaste()!=null) {
					List<AccompPasspDepartment> departments = pod.getAccompPasspWaste().getAccompPassps().getAccompPasspDepartments();

					for (AccompPasspDepartment department : departments) {
						if (department.getDepartment().getId() == departmentFind.getId()) {
							return true;
						}
					}
				}else
				{
					if(pod.getDepartment().getId() == departmentFind.getId()){
						return true;
					}
				}

				return false;
			}
		};

		filter(pod9Dto,pod9Department);

		List<WasteTypeDto> wastes = new ArrayList<>();

		for (Pod9OwnWaste temp: pod9Dto)
		{
			if(temp.getAccompPasspWaste()!=null) {
				wastes.add(wasteTypeMapper.convertToDto(temp.getAccompPasspWaste().getWasteTypes()));
			}else{
				wastes.add(wasteTypeMapper.convertToDto(temp.getWasteType()));
			}
		}

		Integer wasteId = wastes.get(0).getId();

		wastes = wastes.stream().distinct().collect(Collectors.toList());

		List<WasteTypeDto> finalWastes = wastes;
		Predicate pod9Waste = new Predicate() {
			@Override
			public boolean evaluate(Object o) {
				Pod9OwnWaste pod = (Pod9OwnWaste) o;

				if(pod.getAccompPasspWaste()!=null) {
					if (pod.getAccompPasspWaste().getWasteTypes().getId() == finalWastes.get(0).getId()) {
						return true;
					}
				}else
				{
					if(pod.getWasteType().getId()==finalWastes.get(0).getId())
					{
						return true;
					}
				}


				return false;
			}
		};

		filter(pod9Dto,pod9Waste);

		List<Pod9Dto> dtoList = pod9Mapper.convertToDtoList(pod9Dto);

		Pod9Helper pod9Helper = new Pod9Helper();
		pod9Helper.setPod9DtoList(dtoList);
		pod9Helper.setWasteTypeDtos(wastes);
		pod9Helper.setYearMonth(yearMonth);

		return new ResponseEntity<>(pod9Helper, HttpStatus.OK);
	}

	@GetMapping(value = "/{pod9Id}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Pod9SaveDto> getOne(@PathVariable Integer pod9Id) {

		Pod9SaveDto oneSave = pod9OwnWasteService.getOneSave(pod9Id);

		return new ResponseEntity<>(oneSave, HttpStatus.OK);
	}


	@GetMapping(value = "/{departmentId}/{wasteTypeId}/{yearMonth}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Pod9Helper> getAccompPasspByDepartmentIdAndWasteTypeId(@PathVariable Integer departmentId, @PathVariable Integer wasteTypeId, @PathVariable YearMonth yearMonth) {
		DepartmentDto departmentFind = departmentService.getOne(departmentId);
		String month = String.valueOf(yearMonth.getMonthValue());
		String year = String.valueOf(yearMonth.getYear());
		month = month.length() < 2 ? "0" + month : month;
		List<Pod9OwnWaste> pod9Dto = pod9OwnWasteService.getAllByDate(month,year);

		Predicate pod9Department = new Predicate() {
			@Override
			public boolean evaluate(Object o) {
				Pod9OwnWaste pod = (Pod9OwnWaste) o;

				if(pod.getAccompPasspWaste()!=null) {
					List<AccompPasspDepartment> departments = pod.getAccompPasspWaste().getAccompPassps().getAccompPasspDepartments();

					for (AccompPasspDepartment department : departments) {
						if (department.getDepartment().getId() == departmentFind.getId()) {
							return true;
						}
					}
				}else{
					if(pod.getDepartment().getId()==departmentFind.getId()){
						return true;
					}
				}

				return false;
			}
		};

		filter(pod9Dto,pod9Department);

		List<WasteTypeDto> wastes = new ArrayList<>();

		for (Pod9OwnWaste temp: pod9Dto)
		{
			if(temp.getAccompPasspWaste()!=null) {
				wastes.add(wasteTypeMapper.convertToDto(temp.getAccompPasspWaste().getWasteTypes()));
			}else
			{
				wastes.add(wasteTypeMapper.convertToDto(temp.getWasteType()));
			}
		}

		Integer wasteId = wastes.get(0).getId();

		wastes = wastes.stream().distinct().collect(Collectors.toList());

		Predicate pod9Waste = new Predicate() {
			@Override
			public boolean evaluate(Object o) {
				Pod9OwnWaste pod = (Pod9OwnWaste) o;

				if(pod.getAccompPasspWaste()!=null) {
					if (pod.getAccompPasspWaste().getWasteTypes().getId() == wasteTypeId) {
						return true;
					}
				}else
				{
					if(pod.getWasteType().getId()==wasteTypeId){
						return true;
					}
				}


				return false;
			}
		};

		filter(pod9Dto,pod9Waste);

		List<Pod9Dto> dtoList = pod9Mapper.convertToDtoList(pod9Dto);

		Pod9Helper pod9Helper = new Pod9Helper();
		pod9Helper.setPod9DtoList(dtoList);
		pod9Helper.setWasteTypeDtos(wastes);
		pod9Helper.setYearMonth(yearMonth);
		return new ResponseEntity<>(pod9Helper, HttpStatus.OK);
	}

	@GetMapping( value ="/downloadSved",consumes = MediaType.ALL_VALUE )
	public void myExcel(HttpServletResponse response)
			throws IOException, ParseException {

		String reportFile = pod9OwnWasteService.downloadSved();

		byte[] reportContent = reportManager.downloadReport(reportFile);
		try {
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment; filename*=UTF-8''" + "test" + ".xls");
			response.getOutputStream().write(reportContent);
//			outputStream.write(reportContent);
//			outputStream.flush();
//			outputStream.close();
		} catch (IOException e) {
			throw new RuntimeException("Ошибка загрузки файла.");
		}
		//reportManager.removeReport(reportFile);
	}


	@GetMapping( value ="/download/{departmentId}",consumes = MediaType.ALL_VALUE )
	public void myExcel(@PathVariable Integer departmentId, HttpServletResponse response)
			throws IOException, ParseException {


		Department department = departmentDao.findById(departmentId).get();

		String reportFile = pod9OwnWasteService.download(departmentId);

		byte[] reportContent = reportManager.downloadReport(reportFile);
		try {
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment; filename*=UTF-8''" + "test" + ".xls");
			response.getOutputStream().write(reportContent);
//			outputStream.write(reportContent);
//			outputStream.flush();
//			outputStream.close();
		} catch (IOException e) {
			throw new RuntimeException("Ошибка загрузки файла.");
		}
		//reportManager.removeReport(reportFile);
	}

	@PostMapping
	public ResponseEntity<Map<String, String>> addPod9(@Valid @RequestBody Pod9SaveDto pod9SaveDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = errorDataService.fillErrorMap(bindingResult);
			return new ResponseEntity<>(errorMap, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {

			if(pod9SaveDto.getId()!=null){
				pod9OwnWasteService.update(pod9SaveDto);
			}else{
				pod9OwnWasteService.save(pod9SaveDto);
			}
			List<Integer> department = new ArrayList<>();
			List<Integer> wasteTyep = new ArrayList<>();
			department.add(pod9SaveDto.getDepartmentId());
			wasteTyep.add(pod9SaveDto.getWasteTypeId());
			calcCountStoredService.recalcCountStored(wasteTyep,department,pod9SaveDto.getDate());
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}
}
