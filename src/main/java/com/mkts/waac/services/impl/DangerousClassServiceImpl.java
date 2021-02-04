package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.DangerousClassDao;
import com.mkts.waac.Dto.DangerousClassDto;
import com.mkts.waac.mappers.DangerousClassMapper;
import com.mkts.waac.models.AccompPasspWaste;
import com.mkts.waac.models.DangerousClass;
//import com.mkts.waac.mappers.DangerousClassMapper;
import com.mkts.waac.services.DangerousClassService;
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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DangerousClassServiceImpl implements DangerousClassService {


	private DangerousClassDao dangerousClassDao;

	private DangerousClassMapper dangerousClassMapper;

	@Autowired
	public DangerousClassServiceImpl(DangerousClassDao dangerousClassDao, DangerousClassMapper dangerousClassMapper) {
		this.dangerousClassDao = dangerousClassDao;
		this.dangerousClassMapper = dangerousClassMapper;
	}

	@Override
	public List<DangerousClassDto> getAll() {
		List<DangerousClass> alldangerousClass = dangerousClassDao.findAll();
		return dangerousClassMapper.convertToDtoList(alldangerousClass);
	}

	@Override
	public DangerousClassDto getOne(Integer id) {
		DangerousClass dangerousClass = dangerousClassDao.getOne(id);
		return dangerousClassMapper.convertToDto(dangerousClass);
	}

	@Override
	public void save(DangerousClassDto dangerousClassDto) {
		DangerousClass saveEntity = dangerousClassMapper.convertToEntity(dangerousClassDto);
		dangerousClassDao.save(saveEntity);
	}

	@Override
	public void delete(Integer id) {
		dangerousClassDao.deleteById(id);
	}


	@Override
	public String toExcel() {
		XSSFWorkbook workbook =  new XSSFWorkbook();

		List<DangerousClass> all = dangerousClassDao.findAll();

		XSSFSheet sheet = workbook.createSheet("Выборка");
		Integer numberRow = 0;
		numberRow = createRow(sheet, numberRow, true, 1, 1);
		sheet.getRow(numberRow-1).getCell(0).setCellValue("Наименование");

		for (DangerousClass dangerousClass:all){
			numberRow = createRow(sheet, numberRow, true, 1, 3);
			sheet.getRow(numberRow-1).getCell(0).setCellValue(dangerousClass.getName());
		}

		String reportFile = "D:\\test.xls";
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
