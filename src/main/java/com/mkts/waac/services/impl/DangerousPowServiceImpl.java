package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.DangerousPowDao;
import com.mkts.waac.Dto.DangerousPowDto;
import com.mkts.waac.mappers.DangerousPowMapper;
import com.mkts.waac.models.DangerousClass;
import com.mkts.waac.models.DangerousPow;
//import com.mkts.waac.mappers.DangerousPowMapper;
import com.mkts.waac.services.DangerousPowService;
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
public class DangerousPowServiceImpl implements DangerousPowService {

	private DangerousPowDao dangerousPowDao;

	private DangerousPowMapper dangerousPowMapper;

	@Autowired
	public DangerousPowServiceImpl(DangerousPowDao dangerousPowDao, DangerousPowMapper dangerousPowMapper) {
		this.dangerousPowDao = dangerousPowDao;
		this.dangerousPowMapper = dangerousPowMapper;
	}

	@Override
	public List<DangerousPowDto> getAll() {
		List<DangerousPow> allDangerousPow = dangerousPowDao.findAll();
		return dangerousPowMapper.convertToDtoList(allDangerousPow);
	}

	@Override
	public DangerousPowDto getOne(Integer id) {
		DangerousPow dangerousPow = dangerousPowDao.getOne(id);
		return dangerousPowMapper.convertToDto(dangerousPow);
	}

	@Override
	public void save(DangerousPowDto dangerousPowDto) {
		DangerousPow saveEntity = dangerousPowMapper.convertToEntity(dangerousPowDto);
		dangerousPowDao.save(saveEntity);
	}

	@Override
	public void delete(Integer id) {
		dangerousPowDao.deleteById(id);
	}

	@Override
	public String toExcel() {
		XSSFWorkbook workbook =  new XSSFWorkbook();

		List<DangerousPow> all = dangerousPowDao.findAll();

		XSSFSheet sheet = workbook.createSheet("Выборка");
		Integer numberRow = 0;
		numberRow = createRow(sheet, numberRow, true, 1, 1);
		sheet.getRow(numberRow-1).getCell(0).setCellValue("Наименование");

		for (DangerousPow dangerousPow:all){
			numberRow = createRow(sheet, numberRow, true, 1, 1);
			sheet.getRow(numberRow-1).getCell(0).setCellValue(dangerousPow.getName());
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
