package com.mkts.waac.api;

import com.mkts.waac.Dao.DepartmentDao;
import com.mkts.waac.Dto.*;
import com.mkts.waac.mappers.Pod9Mapper;
import com.mkts.waac.mappers.WasteTypeMapper;
import com.mkts.waac.models.AccompPasspDepartment;
import com.mkts.waac.models.Department;
import com.mkts.waac.models.Pod9OwnWaste;
import com.mkts.waac.services.DepartmentService;
import com.mkts.waac.services.Pod10Service;
import com.mkts.waac.services.Pod9OwnWasteService;
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
@RequestMapping(value = "api/pod10",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
public class Pod10Api {

	@Autowired
	private Pod10Service pod10Service;


	@GetMapping(value = "/change-date/{date}",
			consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Pod10Dto> getAccompPasspByDepartmentId(@PathVariable YearMonth date) {


		Pod10Dto pod10Dto = pod10Service.getPod10ByMonth(date);

		return new ResponseEntity<>(pod10Dto, HttpStatus.OK);
	}











}
