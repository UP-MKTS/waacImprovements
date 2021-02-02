package com.mkts.waac.api;


import com.mkts.waac.Dao.Pod9OwnWasteDao;
import com.mkts.waac.Dto.*;
import com.mkts.waac.mappers.Pod9OwnWasteMapper;
import com.mkts.waac.models.*;
import com.mkts.waac.services.*;
import com.mkts.waac.services.helpers.AccompPasspWeightUpdateHelper;
import com.mkts.waac.services.utils.CalcCountStoredService;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/accomp-passp",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AccompPasspApi {


    @Autowired
    private AccompPasspWasteService accompPasspWasteService;

    @Autowired
    private ErrorDataService errorDataService;

    @Autowired
    private AccompPasspReportService accompPasspReportService;

    @Autowired
    private ReportManager reportManager;

    @Autowired
    private AccompPasspService accompPasspService;

    @Autowired
    private CalcCountStoredService calcCountStoredService;

    @Autowired
    private GoalService goalService;

    @Autowired
    private Pod9OwnWasteService pod9OwnWasteService;

    @Autowired
    private Pod9OwnWasteMapper pod9OwnWasteMapper;

    @Autowired
    private Pod9OwnWasteDao pod9OwnWasteDao;

    @GetMapping(value = "/details/{accompPasspId}",
            consumes = MediaType.ALL_VALUE)
    public ResponseEntity<AccompPasspDetailsDto> getAccompPasspDetails(@PathVariable Integer accompPasspId) {
        AccompPasspDetailsDto accompPasspDetailsDto = accompPasspService.getDetailsById(accompPasspId);
        return new ResponseEntity<>(accompPasspDetailsDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{accompPasspId}",
            consumes = MediaType.ALL_VALUE)
    public void delete(@PathVariable Integer accompPasspId){

        accompPasspService.delete(accompPasspId);
    }

    @GetMapping(value = "/{wasteid}/{year}",
            consumes = MediaType.ALL_VALUE)
    public void test(@PathVariable Integer wasteid, @PathVariable String year){
        pod9OwnWasteService.test(wasteid,year);
    }

    @GetMapping(value = "/{accompPasspId}",
            consumes = MediaType.ALL_VALUE)
    public ResponseEntity<AccompPasspDto> getAccompPasspOne(@PathVariable Integer accompPasspId) {
        AccompPasspDto accompPasspDto = accompPasspService.getOne(accompPasspId);
        return new ResponseEntity<>(accompPasspDto, HttpStatus.OK);
    }

    @GetMapping(value = "/update/{accompPasspId}",
            consumes = MediaType.ALL_VALUE)
    public ResponseEntity<AccompPasspWeightUpdateHelper> getAccompPasspForUpdate(@PathVariable Integer accompPasspId) {
        AccompPasspWaste accompPasspWaste = accompPasspWasteService.getOne(accompPasspId);
        List<GoalDto> goalDtos = goalService.getAll();
        AccompPasspDto accompPassp = accompPasspService.getOne(accompPasspWaste.getAccompPassps().getId());
        AccompPasspWasteDto accompPasspWasteDto = accompPasspWasteService.toDto(accompPasspWaste);
        AccompPasspWeightUpdateHelper helper = new AccompPasspWeightUpdateHelper(accompPasspWasteDto,accompPassp,goalDtos);
        return new ResponseEntity<>(helper, HttpStatus.OK);
    }

    @GetMapping(value = "/next-number",
            consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Integer> getNextNumber() {
        Integer nextNumber = accompPasspService.getNextNumber();
        return new ResponseEntity<>(nextNumber, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addAccompPassp(@Valid @RequestBody AccompPasspSaveDto accompPasspSaveDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = errorDataService.fillErrorMap(bindingResult);
            return new ResponseEntity<>(errorMap, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        } else {

            if(accompPasspSaveDto.getId()==null) {
                accompPasspService.newAccopmPassp(accompPasspSaveDto);
//                calcCountStoredService.recalcCountStored(accompPasspSaveDto.getWasteTypeIdList(),accompPasspSaveDto.getDepartmentList(), accompPasspSaveDto.getTransportationDate());
            }else {
                accompPasspService.saveUpdete(accompPasspSaveDto);
//                calcCountStoredService.recalcCountStored(accompPasspSaveDto.getWasteTypeIdList(),accompPasspSaveDto.getDepartmentList(), accompPasspSaveDto.getTransportationDate());
            }
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/weight",
            consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<AccompPasspDto>> getAccompPasspWeights() {
        List<AccompPasspDto> accompPasspDtos = accompPasspService.getAll();
        return new ResponseEntity<>(accompPasspDtos, HttpStatus.OK);
    }

    @PostMapping(value = "/weight/{accompPasspWasteId}/{goalId}/{weight}",
            consumes = MediaType.ALL_VALUE)
    public ResponseEntity<String> addAccompPasspWeights(@PathVariable Integer accompPasspWasteId, @PathVariable Integer goalId, @PathVariable Double weight) {
        accompPasspService.saveWeightAndGoal(accompPasspWasteId,goalId,weight);
        Pod9OwnWaste  pod9OwnWaste = pod9OwnWasteService.getBuAccompPasspWasteId(accompPasspWasteId);
        List<Integer> departments = new ArrayList<>();
        List<Integer> wasteTypes = new ArrayList<>();
        AccompPasspWaste accompPasspWaste = accompPasspWasteService.getOne(accompPasspWasteId);
        for (AccompPasspDepartment department:accompPasspWaste.getAccompPassps().getAccompPasspDepartments())
        {
            departments.add(department.getDepartment().getId());
        }
        wasteTypes.add(accompPasspWaste.getWasteTypes().getId());
        if(pod9OwnWaste.getDepartment()!=null) {
            departments.add(pod9OwnWaste.getDepartment().getId());
        }
        Pod9OwnWasteDto pod9OwnWasteDto = pod9OwnWasteMapper.convertToDto(pod9OwnWaste);
//        calcCountStoredService.recalcCountStored(wasteTypes,departments,pod9OwnWasteDto.getTransparentDate());
        return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
    }

    @GetMapping( value ="/download/{accompPasspId}",consumes = MediaType.ALL_VALUE )
    public void myExcel(@PathVariable Integer accompPasspId, HttpServletResponse response)
            throws IOException, ParseException {

        String reportFile = accompPasspReportService.createAccompPasspReport(accompPasspId);

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

}
