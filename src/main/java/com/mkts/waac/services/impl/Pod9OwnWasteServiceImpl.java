package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.AccompPasspWasteDao;
import com.mkts.waac.Dao.DepartmentDao;
import com.mkts.waac.Dao.Pod9OwnWasteDao;
import com.mkts.waac.Dao.WasteTypeDao;
import com.mkts.waac.Dto.DepartmentDto;
import com.mkts.waac.Dto.Pod9OwnWasteDto;
import com.mkts.waac.Dto.Pod9SaveDto;
import com.mkts.waac.Dto.WasteTypeDto;
import com.mkts.waac.mappers.DepartmentMapper;
import com.mkts.waac.mappers.Pod9OwnWasteMapper;
import com.mkts.waac.mappers.WasteTypeMapper;
import com.mkts.waac.models.*;
import com.mkts.waac.services.Pod9OwnWasteService;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Predicate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;


import static org.springframework.cglib.core.CollectionUtils.filter;

@Service
@Transactional
public class Pod9OwnWasteServiceImpl implements Pod9OwnWasteService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private Pod9OwnWasteDao pod9OwnWasteDao;

    @Autowired
    private Pod9OwnWasteMapper pod9OwnWasteMapper;

    @Autowired
    private WasteTypeDao wasteTypeDao;

    @Autowired
    private WasteTypeMapper wasteTypeMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private AccompPasspWasteDao accompPasspWasteDao;

    @Override
    public void save(AccompPasspWaste accompPasspWaste) {
        Pod9OwnWaste pod9OwnWaste = new Pod9OwnWaste();
        pod9OwnWaste.setAccompPasspWaste(accompPasspWaste);
        pod9OwnWaste.setTransparentDate(accompPasspWaste.getAccompPassps().getTransportationDate());
        pod9OwnWasteDao.save(pod9OwnWaste);
    }

    @Override
    public List<Pod9OwnWaste> getAllByDate(String month, String year) {
        return pod9OwnWasteDao.getAllByDate(month,year);
    }

    @Override
    public void deleteByAccopmPasspWasteId(Integer accompPasspWasteId) {
        pod9OwnWasteDao.deleteByAccompPasspWaste_Id(accompPasspWasteId);
    }

    @Override
    public Pod9OwnWasteDto toDto(Pod9OwnWaste pod9OwnWaste) {
        return pod9OwnWasteMapper.convertToDto(pod9OwnWaste);
    }

    @Override
    public Pod9SaveDto getOneSave(Integer pod9Id) {

        return pod9OwnWasteMapper.POD_9_OWN_WASTE_TO_SAVE(pod9OwnWasteDao.findById(pod9Id).get());
    }

    @Override
    public Pod9OwnWaste getOne(Integer id) {
        return pod9OwnWasteDao.findById(id).get();
    }

    @Override
    public Pod9OwnWaste getBuAccompPasspWasteId(Integer accompPasspWasteId) {
        return pod9OwnWasteDao.findByAccompPasspWaste_Id(accompPasspWasteId);
    }

    @Override
    public void save(Pod9OwnWaste pod9OwnWaste) {
        pod9OwnWasteDao.save(pod9OwnWaste);
    }

    @Override
    public List<Pod9OwnWaste> getPod9ByByMonthAndYear(String month, String year) {
        return pod9OwnWasteDao.findAllByDate(month,year);
    }

    @Override
    public void save(Pod9SaveDto pod9SaveDto) {
        Pod9OwnWasteDto pod9OwnWaste = pod9OwnWasteMapper.convertToDto(new Pod9OwnWaste());
        pod9OwnWaste.setTransparentDate(pod9SaveDto.getDate());
        WasteTypeDto wasteTypeDto = wasteTypeMapper.convertToDto(wasteTypeDao.findById(pod9SaveDto.getWasteTypeId()).get());
        DepartmentDto departmentDto = departmentMapper.convertToDto(departmentDao.findById(pod9SaveDto.getDepartmentId()).get());
        pod9OwnWaste.setWasteType(wasteTypeDto);
        pod9OwnWaste.setCountFromOther(pod9SaveDto.getCountFromOther());
        pod9OwnWaste.setCountFromPeople(pod9SaveDto.getCountFromPeople());
        pod9OwnWaste.setCountNeutralized(pod9SaveDto.getCountNeutralized());
        pod9OwnWaste.setCountUsed(pod9SaveDto.getCountUsed());
        pod9OwnWaste.setDepartment(departmentDto);
        pod9OwnWaste.setNameOther(pod9SaveDto.getNameOther());
        pod9OwnWaste.setWasteGenerate(pod9SaveDto.getWasteGenerate());
        pod9OwnWasteDao.save(pod9OwnWasteMapper.convertToEntity(pod9OwnWaste));
    }

    public void update(Pod9SaveDto pod9SaveDto) {
        Pod9OwnWasteDto pod9OwnWaste = pod9OwnWasteMapper.convertToDto(pod9OwnWasteDao.findById(pod9SaveDto.getId()).get());
        WasteTypeDto wasteTypeDto = wasteTypeMapper.convertToDto(wasteTypeDao.findById(pod9SaveDto.getWasteTypeId()).get());
        DepartmentDto departmentDto = departmentMapper.convertToDto(departmentDao.findById(pod9SaveDto.getDepartmentId()).get());
        pod9OwnWaste.setTransparentDate(pod9SaveDto.getDate());
        pod9OwnWaste.setWasteType(wasteTypeDto);
        pod9OwnWaste.setCountFromOther(pod9SaveDto.getCountFromOther());
        pod9OwnWaste.setCountFromPeople(pod9SaveDto.getCountFromPeople());
        pod9OwnWaste.setCountNeutralized(pod9SaveDto.getCountNeutralized());
        pod9OwnWaste.setCountUsed(pod9SaveDto.getCountUsed());
        pod9OwnWaste.setDepartment(departmentDto);
        pod9OwnWaste.setNameOther(pod9SaveDto.getNameOther());
        pod9OwnWaste.setWasteGenerate(pod9SaveDto.getWasteGenerate());
        pod9OwnWasteDao.save(pod9OwnWasteMapper.convertToEntity(pod9OwnWaste));
    }

    @Override
    public List<Pod9OwnWasteDto> getAllByWasteTypeId(Integer wasteType) {

        List<Pod9OwnWaste> allByAccompPasspWaste_wasteTypes_id = pod9OwnWasteDao.findAllByAccompPasspWaste_WasteTypes_Id(wasteType);
        return pod9OwnWasteMapper.convertToDtoList(allByAccompPasspWaste_wasteTypes_id);

    }

    @Override
    public List<Pod9OwnWaste> getAll() {

        return pod9OwnWasteDao.getAll();
    }

    @Override
    public void test(Integer wasteId, String year) {
        List<Pod9OwnWaste> all = pod9OwnWasteDao.findAll();
        List<Pod9OwnWaste> pod9ByWasteIdAndYear = pod9OwnWasteDao.getPod9ByWasteIdAndYear(wasteId, year);
    }

    @Override
    public void delete(Integer id) {
        Pod9OwnWaste pod9OwnWaste = pod9OwnWasteDao.findById(id).get();
        pod9OwnWasteDao.delete(pod9OwnWaste);
    }

    @Autowired
    private Pod9OwnWasteService pod9OwnWasteService;

    class StylesBorder{

        XSSFCellStyle borderStyleT;

        XSSFCellStyle borderStyleLine;

        public StylesBorder(XSSFWorkbook wb) {

            borderStyleLine = wb.createCellStyle();
            borderStyleT = wb.createCellStyle();
            borderStyleT.setBorderBottom(BorderStyle.THIN);
            borderStyleT.setBorderLeft(BorderStyle.THIN);
            borderStyleT.setBorderRight(BorderStyle.THIN);
            borderStyleT.setBorderTop(BorderStyle.THIN);
            borderStyleLine.setBorderBottom(BorderStyle.THIN);
            borderStyleLine.setBorderLeft(BorderStyle.NONE);
            borderStyleLine.setBorderRight(BorderStyle.NONE);
            borderStyleLine.setBorderTop(BorderStyle.NONE);

        }


    }

    public String downloadSved()throws IOException{
        XSSFWorkbook workbook =  new XSSFWorkbook();
        Pod9OwnWasteServiceImpl.StylesBorder stylesBorder = new Pod9OwnWasteServiceImpl.StylesBorder(workbook);

        List<AccompPasspWaste> all = accompPasspWasteDao.findAll();

        List<AccompPasspWaste> nullAll = new ArrayList<>();
        Date now = new Date();
        LocalDate date = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        for (AccompPasspWaste accompPasspWaste: all){
            if(accompPasspWaste.getWasteWeight()==0.0 && (accompPasspWaste.getAccompPassps().getTransportationDate().getDayOfMonth()<date.getDayOfMonth() || accompPasspWaste.getAccompPassps().getTransportationDate().getMonthValue()<date.getMonthValue()))
            {
                nullAll.add(accompPasspWaste);
            }
        }

        XSSFSheet sheet = workbook.createSheet("Выборка");
        Integer numberRow = 0;
        numberRow = createRow(sheet, numberRow, true, 1, 3);
        sheet.getRow(numberRow-1).getCell(0).setCellValue("Код отхода");
        sheet.getRow(numberRow-1).getCell(1).setCellValue("Дата вывоза");
        sheet.getRow(numberRow-1).getCell(2).setCellValue("Подразделение");

        for (AccompPasspWaste waste:nullAll){
            numberRow = createRow(sheet, numberRow, true, 1, 3);
            sheet.getRow(numberRow-1).getCell(0).setCellValue(waste.getWasteTypes().getCode());
            sheet.getRow(numberRow-1).getCell(1).setCellValue(dateConverter(waste.getAccompPassps().getTransportationDate(),"."));
            sheet.getRow(numberRow-1).getCell(2).setCellValue(waste.getAccompPassps().getDepartmentsShortName(","));
        }

        String reportFile = "D:\\Lagvinovich\\Проекты\\test.xls";
//		String reportFile = "E:\\Projects\\temp\\test.xls";

        try {
            FileOutputStream out = new FileOutputStream(new File(reportFile));
            try {
                workbook.write(out);
            } catch (IOException e) {
                throw new RuntimeException("Ошибка создания файла отчета. Данные не были записанны.");
            }

            try {
                out.close();
            } catch (IOException e) {
                throw new RuntimeException("Ошибка закрытия файла отчета.");
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Ошибка инициализации файла для записи данных отчета.");
        }

        try {
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка закрытия рабочей книги отчета.");
        }
        return reportFile;
    }

    @Override
    public String download(Integer departmentId) throws IOException{
        XSSFWorkbook workbook =  new XSSFWorkbook();
        Pod9OwnWasteServiceImpl.StylesBorder stylesBorder = new Pod9OwnWasteServiceImpl.StylesBorder(workbook);

        List<Pod9OwnWaste> pod9Dto = pod9OwnWasteService.getAll();

        Predicate pod9Department = new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                Pod9OwnWaste pod = (Pod9OwnWaste) o;

                if(pod.getAccompPasspWaste()!=null) {
                    List<AccompPasspDepartment> departments = pod.getAccompPasspWaste().getAccompPassps().getAccompPasspDepartments();

                    for (AccompPasspDepartment department : departments) {
                        if (department.getDepartment().getId() == departmentId) {
                            return true;
                        }
                    }
                }else
                {
                    if(pod.getDepartment().getId()==departmentId)
                    {
                        return true;
                    }
                }

                return false;
            }
        };

        filter(pod9Dto,pod9Department);
        pod9Dto.sort(Pod9OwnWaste::compareTo);
        List<Integer> wasteTypes = new ArrayList<>();
        for (Pod9OwnWaste pod9OwnWaste: pod9Dto)
        {
            if(pod9OwnWaste.getAccompPasspWaste()!=null) {
                wasteTypes.add(pod9OwnWaste.getAccompPasspWaste().getWasteTypes().getCode());
            }else{
                wasteTypes.add(pod9OwnWaste.getWasteType().getCode());
            }
        }

        wasteTypes = wasteTypes.stream().distinct().collect(Collectors.toList());
        Integer temp = wasteTypes.get(0);
        for (Integer waste:wasteTypes)
        {
            List<Pod9OwnWaste> pod9 = new ArrayList<>();
            String code = "";
            for (Pod9OwnWaste pod9OwnWaste:pod9Dto) {

                if(pod9OwnWaste.getAccompPasspWaste()!=null) {
                    if (pod9OwnWaste.getAccompPasspWaste().getWasteTypes().getCode() == waste) {
                        pod9.add(pod9OwnWaste);
                        code = pod9OwnWaste.getAccompPasspWaste().getWasteTypes().getCode().toString();
                    }
                }else
                {
                    if(pod9OwnWaste.getWasteType().getCode()==waste)
                    {
                        pod9.add(pod9OwnWaste);
                        code = pod9OwnWaste.getWasteType().getCode().toString();
                    }
                }
            }

            XSSFSheet sheet = workbook.createSheet(code);
            Integer numberRow = 0;
            numberRow = createRow(sheet, numberRow, true, 2, 11);
            sheet.addMergedRegion(new CellRangeAddress(numberRow - 2, numberRow - 2, 0, 10));
            sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 0, 6));
            sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 9, 10));

            WasteType wasteType;
            if(pod9.get(0).getAccompPasspWaste()!=null)
            {
                wasteType = pod9.get(0).getAccompPasspWaste().getWasteTypes();
            }else
            {
                wasteType = pod9.get(0).getWasteType();
            }

            XSSFCell cell = sheet.getRow(numberRow - 1).getCell(0);
            cell.setCellValue(wasteType.getName());
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 10, IndexedColors.BLACK, true, false));

            cell = sheet.getRow(numberRow - 1).getCell(8);
            cell.setCellValue(wasteType.getCode());
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 10, IndexedColors.BLACK, true, false));

            cell = sheet.getRow(numberRow - 1).getCell(9);
            cell.setCellValue(wasteType.getDangerousPow().getName()+" - "+wasteType.getDangerousClass().getName()+" класс");
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 10, IndexedColors.BLACK, true, false));


            //ROW
            numberRow = createRow(sheet, numberRow, true, 1, 11);
            sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 0, 6));
            sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 9, 10));

            cell = sheet.getRow(numberRow - 1).getCell(0);
            cell.setCellValue("(наименование отхода)");
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 6, IndexedColors.BLACK, false, true));

            cell = sheet.getRow(numberRow - 1).getCell(8);
            cell.setCellValue("(код отхода)");
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 6, IndexedColors.BLACK, false, true));

            cell = sheet.getRow(numberRow - 1).getCell(9);
            cell.setCellValue("(степень опасности или класс опасности отхода)");
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false,null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 6, IndexedColors.BLACK, false, true));

            numberRow = createRow(sheet, numberRow, true, 1, 11);
            sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 0, 10));
            cell = sheet.getRow(numberRow - 1).getCell(0);
            cell.setCellValue(wasteType.getWasteNorm());
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 10, IndexedColors.BLACK, true, false));

            numberRow = createRow(sheet, numberRow, true, 1, 11);
            sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 0, 10));
            cell = sheet.getRow(numberRow - 1).getCell(0);
            cell.setCellValue("(норматив образования отхода)");
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 6, IndexedColors.BLACK, false, true));

            numberRow = createRow(sheet, numberRow, true, 1, 11);
            sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 0, 10));
            cell = sheet.getRow(numberRow - 1).getCell(0);
            cell.setCellValue(wasteType.getActivityKinds().getName());
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 10, IndexedColors.BLACK, true, false));

            numberRow = createRow(sheet, numberRow, true, 1, 11);
            sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 0, 10));
            cell = sheet.getRow(numberRow - 1).getCell(0);
            cell.setCellValue("(наименование вида деятельности и (или) технологического процесса, в результате которого образуются отходы)");
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 6, IndexedColors.BLACK, false, true));


            numberRow = createRow(sheet,numberRow,true,2,11);
            sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-1, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-1, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-2, 2, 3));
            sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-1, 4, 4));
            sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-1, 5, 5));
            sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-1, 6, 6));
            sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-2, 7, 9));
            sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-1, 10, 10));

            sheet.getRow(numberRow-2).getCell(0).setCellValue("Дата");
            sheet.getRow(numberRow-2).getCell(1).setCellValue("Образовалось,т(шт.)");

            sheet.getRow(numberRow-2).getCell(2).setCellValue("Поступило от других организаций, структурных подразделений");
            sheet.getRow(numberRow-1).getCell(2).setCellValue("количество, т (шт.)");
            sheet.getRow(numberRow-1).getCell(3).setCellValue("наименование организации, структурного подразделения");

            sheet.getRow(numberRow-2).getCell(4).setCellValue("Поступило от физических лиц, т. (шт.)");

            sheet.getRow(numberRow-2).getCell(5).setCellValue("Использовано, т (шт.)");

            sheet.getRow(numberRow-2).getCell(6).setCellValue("Обезврежено, т (шт.)");
            sheet.getRow(numberRow-2).getCell(7).setCellValue("Передано на использование, обезвреживание, хранение, захоронение");
            sheet.getRow(numberRow-1).getCell(7).setCellValue("количество, т (шт.)");

            sheet.getRow(numberRow-1).getCell(8).setCellValue("наименование организации, структурного подразделения");

            sheet.getRow(numberRow-1).getCell(9).setCellValue("цель");
            sheet.getRow(numberRow-2).getCell(10).setCellValue("Хранится, т (шт.)");;

            numberRow = createRow(sheet,numberRow,true,1,11);

            for (int i = 0; i<sheet.getRow(numberRow-1).getLastCellNum();i++){
                sheet.getRow(numberRow-1).getCell(i).setCellValue(i+1);
            }
            for (int i =1;i<4; i++){
                for (int j = 0; j<sheet.getRow(numberRow-i).getLastCellNum();j++){
                    sheet.getRow(numberRow-i).getCell(j).setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,true,stylesBorder.borderStyleT));
                    sheet.getRow(numberRow-i).getCell(j).getCellStyle().setFont(setFont(workbook,(short)9,IndexedColors.BLACK,true,false));
                }
            }
            for (Pod9OwnWaste pod9OwnWaste: pod9){
                numberRow = createRow(sheet,numberRow,true,1,11);
                XSSFRow row = sheet.getRow(numberRow-1);
                row.getCell(0).setCellValue(dateConverter(pod9OwnWaste.getTransparentDate(),"."));
                row.getCell(1).setCellValue(setVal(pod9OwnWaste.getWasteGenerate()));
                row.getCell(2).setCellValue(setVal(pod9OwnWaste.getCountFromOther()));

                row.getCell(3).setCellValue(setVal(pod9OwnWaste.getCountFromOther()));
                row.getCell(4).setCellValue(setVal(pod9OwnWaste.getNameOther()));
                row.getCell(5).setCellValue(setVal(pod9OwnWaste.getCountUsed()));
                row.getCell(6).setCellValue(setVal(pod9OwnWaste.getCountNeutralized()));
                row.getCell(7).setCellValue(pod9OwnWaste.getAccompPasspWaste()!=null?setVal(pod9OwnWaste.getAccompPasspWaste().getWasteWeight()):"-");
                row.getCell(8).setCellValue(pod9OwnWaste.getAccompPasspWaste()!=null?setVal(pod9OwnWaste.getAccompPasspWaste().getAccompPassps().getContract().getOrganization().getName()):"-");
                row.getCell(9).setCellValue(pod9OwnWaste.getAccompPasspWaste()!=null?setVal(pod9OwnWaste.getAccompPasspWaste().getGoal().getName()):"-");
                row.getCell(10).setCellValue(setVal(pod9OwnWaste.getCountKeeping()));

                for (int j = 0; j<sheet.getRow(numberRow-1).getLastCellNum();j++){
                    sheet.getRow(numberRow-1).getCell(j).setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,stylesBorder.borderStyleT));
                    sheet.getRow(numberRow-1).getCell(j).getCellStyle().setFont(setFont(workbook,(short)10,IndexedColors.BLACK,false,false));
                }

            }
            numberRow = createRow(sheet,numberRow,true,2,11);
            sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 3));
            cell = sheet.getRow(numberRow-1).getCell(0);
            cell.setCellValue("Ответственный за ведение книги");
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 10, IndexedColors.BLACK, false, false));

            Department department = departmentDao.findById(departmentId).get();

            numberRow = createRow(sheet,numberRow,true,1,11);
            sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 4));
            sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 8, 9));
            cell = sheet.getRow(numberRow-1).getCell(0);
            cell.setCellValue(department.getChiefPosition());
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 10, IndexedColors.BLACK, false, false));
            cell = sheet.getRow(numberRow-1).getCell(8);
            cell.setCellValue(department.getChiefFio());
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 10, IndexedColors.BLACK, false, false));

            numberRow = createRow(sheet,numberRow,true,1,11);
            sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 4));
            sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 5, 6));
            sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 8, 9));
            cell = sheet.getRow(numberRow-1).getCell(0);
            cell.setCellValue("(должность)");
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 6, IndexedColors.BLACK, false, true));
            cell = sheet.getRow(numberRow-1).getCell(8);
            cell.setCellValue("(инициалы, фамилия)");
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 6, IndexedColors.BLACK, false, true));
            cell = sheet.getRow(numberRow-1).getCell(5);
            cell.setCellValue("(подпись)");
            cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
            cell.getCellStyle().setFont(setFont(workbook, (short) 6, IndexedColors.BLACK, false, true));

        }




        String reportFile = "D:\\Lagvinovich\\Проекты\\test.xls";
//		String reportFile = "E:\\Projects\\temp\\test.xls";

        try {
            FileOutputStream out = new FileOutputStream(new File(reportFile));
            try {
                workbook.write(out);
            } catch (IOException e) {
                throw new RuntimeException("Ошибка создания файла отчета. Данные не были записанны.");
            }

            try {
                out.close();
            } catch (IOException e) {
                throw new RuntimeException("Ошибка закрытия файла отчета.");
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Ошибка инициализации файла для записи данных отчета.");
        }

        try {
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка закрытия рабочей книги отчета.");
        }
        return reportFile;
    }


    private String setVal(Object value)
    {
        return value==null?"-":value.toString();
    }

    private int createRow(XSSFSheet sheet,int numRow, boolean createCells, int countIterations, int countCol){
        XSSFRow newRow = sheet.createRow(numRow);
        numRow++;
        if(createCells) {
            createCells(newRow, countCol);
        }
        return countIterations>1?createRow(sheet,numRow,true,countIterations-1,countCol):numRow;
    }

    private XSSFCellStyle setCellAlignmeng(XSSFWorkbook wb, HorizontalAlignment alignmentH, VerticalAlignment alignmentV, boolean wrap, XSSFCellStyle borderstyle){
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(alignmentH);
        style.setVerticalAlignment(alignmentV);
        style.setWrapText(wrap);
        if(borderstyle!=null){
            style.setBorderBottom(borderstyle.getBorderBottom());
            style.setBorderTop(borderstyle.getBorderTop());
            style.setBorderLeft(borderstyle.getBorderLeft());
            style.setBorderRight(borderstyle.getBorderRight());
        }
        return style;
    }

    private XSSFFont setFont(XSSFWorkbook workbook, Short HeightInPoints, IndexedColors color, boolean bold, boolean italic){
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(HeightInPoints);
        font.setFontName("Times New Roman");
        font.setColor(color.getIndex());
        font.setBold(bold);
        font.setItalic(italic);
        return font;
    }

    private void createCells(XSSFRow row, int count){
        for (int cl=0;cl<count;cl++){
            row.createCell(cl);
        }
    }

    private String dateConverter(LocalDate date, String splitter){
        String temp = date.toString();
        String[]temp2 = temp.split("-");
        return temp2[2]+splitter+temp2[1]+splitter+temp2[0];
    }
//

//
//	private AccompPasspDao accompPasspDao;
//
//	private Pod9OwnWasteMapper pod9OwnWasteMapper;
//
//	@Autowired
//	public Pod9OwnWasteServiceImpl(Pod9OwnWasteDao pod9OwnWasteDao, AccompPasspDao accompPasspDao, Pod9OwnWasteMapper pod9OwnWasteMapper) {
//		this.pod9OwnWasteDao = pod9OwnWasteDao;
//		this.accompPasspDao = accompPasspDao;
//		this.pod9OwnWasteMapper = pod9OwnWasteMapper;
//	}
//
//	@Override
//	public List<Pod9OwnWasteDto> getAll() {
//		List<Pod9OwnWaste> allPod9OwnWaste = pod9OwnWasteDao.findAll();
//		return pod9OwnWasteMapper.convertToDtoList(allPod9OwnWaste);
//	}
//
//	@Override
//	public List<Pod9OwnWasteDto> getPod9OwnByDepartmentId(Integer id) {
//		List<Pod9OwnWaste> allPod9OwnWaste = pod9OwnWasteDao.findAllByDepartmentId(id);
//		return pod9OwnWasteMapper.convertToDtoList(allPod9OwnWaste);
//	}
//
//	@Override
//	public List<Pod9WasteDto> getPod9OwnByDepartmentIdAndWasteTypeId(Integer departmentId, Integer wasteTypeId) {
//		List<Pod9OwnWaste> allPod9OwnWaste = pod9OwnWasteDao.findAllByDepartmentIdAndWasteTypeIdOrderByDate(departmentId, wasteTypeId);
//		return pod9OwnWasteMapper.convertToWasteDtoList(allPod9OwnWaste);
//	}
//
//	@Override
//	public Pod9OwnWasteDto getOne(Integer id) {
//		Pod9OwnWaste pod9OwnWaste = pod9OwnWasteDao.getOne(id);
//		return pod9OwnWasteMapper.convertToDto(pod9OwnWaste);
//	}
//
//	@Override
//	public void save(Pod9OwnWasteDto pod9OwnWasteDto) {
//		Pod9OwnWaste saveEntity = pod9OwnWasteMapper.convertToEntity(pod9OwnWasteDto);
//		pod9OwnWasteDao.save(saveEntity);
//	}
//
//	@Override
//	public void delete(Integer id) {
//		pod9OwnWasteDao.deleteById(id);
//	}
//
//	@Override
//	public List<Pod10Dto> getOwnWasteByMonth(String month) {
//		List<Pod9OwnWaste> pod9OwnWastes = pod9OwnWasteDao.findAllByMonth(month);
//		return pod9OwnWasteMapper.convertToOwnDtoList(pod9OwnWastes);
//	}
//
//	@Override
//	public Map<Integer, Double> getCarriedOverBalances(LocalDate date) {
//		List<Integer> carriedOverBalanceWasteId = pod9OwnWasteDao.findUniqWasteTypeId(date);
//		Map<Integer, Double> carriedOverBalances = new HashMap<>();
//		for (Integer id : carriedOverBalanceWasteId) {
//			Double sumGenerated = NumHelper.isNull(pod9OwnWasteDao.sumWasteGenerated(id, date));
//			Double carriedOverBalanceWaste = NumHelper.isNull(pod9OwnWasteDao.sumCarriedOverBalances(id, date));
//			Double setTransferred = NumHelper.isNull(accompPasspDao.sumWasteTransferred(id, date));
//			Double wasteCountStored = NumHelper.round(sumGenerated + carriedOverBalanceWaste - setTransferred);
//			carriedOverBalances.put(id, wasteCountStored);
//		}
//		return  carriedOverBalances;
//	}
}
