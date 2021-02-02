package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.WasteTypeDao;
import com.mkts.waac.Dto.WasteTypeDto;
import com.mkts.waac.models.DangerousClass;
import com.mkts.waac.models.WasteType;
import com.mkts.waac.mappers.WasteTypeMapper;
import com.mkts.waac.services.WasteTypeService;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class WasteTypeServiceImpl implements WasteTypeService {


	private WasteTypeDao wasteTypeDao;

	private WasteTypeMapper wasteTypeMapper;

	@Autowired
	public WasteTypeServiceImpl(WasteTypeDao wasteTypeDao, WasteTypeMapper wasteTypeMapper) {
		this.wasteTypeDao = wasteTypeDao;
		this.wasteTypeMapper = wasteTypeMapper;
	}

	@Override
	public List<WasteTypeDto> getAll() {
		List<WasteType> allWasteTypes = wasteTypeDao.findAll();
		return wasteTypeMapper.convertToDtoList(allWasteTypes);
	}

	@Override
	public List<WasteTypeDto> getAllByListId(List<Integer> indexes) {
		List<WasteType> wasteTypes = wasteTypeDao.findAllById(indexes);
		return wasteTypeMapper.convertToDtoList(wasteTypes);
	}

	@Override
	public WasteTypeDto getOne(Integer id) {
		WasteType wasteType = wasteTypeDao.getOne(id);
		return wasteTypeMapper.convertToDto(wasteType);
	}

	@Override
	public void save(WasteTypeDto wasteTypeDto) {
		WasteType saveEntity = wasteTypeMapper.convertToEntity(wasteTypeDto);
		wasteTypeDao.save(saveEntity);
	}

	@Override
	public void delete(Integer id) {
		wasteTypeDao.deleteById(id);
	}

	@Override
	public List<WasteTypeDto> getFromAccompPassp(Integer departmentId) {
		return null;
	}
	//	@Override
//	public List<WasteTypeDto> getFromAccompPassp(Integer departmentId) {
//		List<WasteType> wasteType = wasteTypeDao.findWasteTypeByDepartmentId(departmentId);
//		return wasteTypeMapper.convertToDtoList(wasteType);
//	}

	@Override
	public String toExcel() {
		XSSFWorkbook workbook =  new XSSFWorkbook();

		List<WasteType> all = wasteTypeDao.findAll();

		XSSFSheet sheet = workbook.createSheet("Выборка");
		Integer numberRow = 0;
		numberRow = createRow(sheet, numberRow, true, 1, 6);
		sheet.getRow(numberRow-1).getCell(0).setCellValue("Код отхода");
		sheet.getRow(numberRow-1).getCell(1).setCellValue("Наименование");
		sheet.getRow(numberRow-1).getCell(2).setCellValue("Степень опасности");
		sheet.getRow(numberRow-1).getCell(3).setCellValue("Класс опасности");
		sheet.getRow(numberRow-1).getCell(4).setCellValue("Вид деятельности");
		sheet.getRow(numberRow-1).getCell(5).setCellValue("Норматив образования");

		for (WasteType wasteType:all){
			numberRow = createRow(sheet, numberRow, true, 1, 6);
			sheet.getRow(numberRow-1).getCell(0).setCellValue(wasteType.getCode());
			sheet.getRow(numberRow-1).getCell(1).setCellValue(wasteType.getName());
			sheet.getRow(numberRow-1).getCell(2).setCellValue(wasteType.getDangerousPow().getName());
			sheet.getRow(numberRow-1).getCell(3).setCellValue(wasteType.getDangerousClass().getName());
			sheet.getRow(numberRow-1).getCell(4).setCellValue(wasteType.getActivityKinds().getName());
			sheet.getRow(numberRow-1).getCell(5).setCellValue(wasteType.getWasteNorm());

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
}
