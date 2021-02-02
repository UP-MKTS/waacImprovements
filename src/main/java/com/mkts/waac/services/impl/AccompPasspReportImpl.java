package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.AccompPasspDao;
import com.mkts.waac.Dao.WasteTypeDao;
import com.mkts.waac.Dto.AccompPasspDto;
import com.mkts.waac.Dto.AccompPasspJournalDto;
import com.mkts.waac.Dto.AccompPasspWasteDto;
import com.mkts.waac.models.AccompPassp;
import com.mkts.waac.models.AccompPasspDepartment;
import com.mkts.waac.models.AccompPasspWaste;
import com.mkts.waac.models.WasteType;
import com.mkts.waac.services.AccompPasspReportService;

import com.mkts.waac.services.AccompPasspService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.ScatterChartSeries;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class AccompPasspReportImpl implements AccompPasspReportService {

	private static final String PATH = "D:\\Lagvinovich\\Проекты\\";
	private static final String fileAccomp = "СП 725 ТЗУ подметь от 25.09.2020.xlsx";

	@Autowired
	private AccompPasspDao accompPasspDao;

	@Autowired
	AccompPasspService accompPasspService;

	@Autowired
	private WasteTypeDao wasteTypeDao;

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
			borderStyleLine.setBorderBottom(BorderStyle.NONE);
			borderStyleLine.setBorderLeft(BorderStyle.NONE);
			borderStyleLine.setBorderRight(BorderStyle.NONE);
			borderStyleLine.setBorderTop(BorderStyle.NONE);

		}


	}

	@Override
	public String createAccompPasspReport(int id) throws IOException {

		AccompPassp accompPassp = accompPasspDao.findFirstById(id);
		XSSFWorkbook workbook =  new XSSFWorkbook();
		StylesBorder stylesBorder = new StylesBorder(workbook);

		XSSFSheet sheet = workbook.createSheet("Сопроводительный");
		//Размеры колонок
		sheet.setColumnWidth(0,2740);
		sheet.setColumnWidth(1,2707);
		sheet.setColumnWidth(2,3460);
		sheet.setColumnWidth(3,2123);
		sheet.setColumnWidth(4,2397);
		sheet.setColumnWidth(5,2877);
		sheet.setColumnWidth(6,1967);
		sheet.setColumnWidth(7,1885);
		sheet.setColumnWidth(8,2060);
		sheet.setColumnWidth(9,4247);
		sheet.setColumnWidth(10,2671);
		sheet.setColumnWidth(11,2159);

		Short sizeExplanations = 6;
		Short sizePasted = 14;


		Integer numberRow = 0;
		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));

		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));
		XSSFCell cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("СОПРОВОДИТЕЛЬНЫЙ ПАСПОРТ");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,(short)16,IndexedColors.BLACK,true,false));

		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));
		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("перевозки отходов производства");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,(short)16,IndexedColors.BLACK,true,false));

		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 2, 11));
		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("Регистрационный номер");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.LEFT,VerticalAlignment.CENTER,false, null));
		cell.getCellStyle().setFont(setFont(workbook,(short)10,IndexedColors.BLACK,false,false));

		cell = sheet.getRow(numberRow-1).getCell(2);
		cell.setCellValue(String.valueOf(accompPassp.getNumber())+" от "+dateConverter(accompPassp.getTransportationDate(),"."));
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false, null));
		cell.getCellStyle().setFont(setFont(workbook,sizePasted,IndexedColors.BLACK,true,false));

		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 2, 11));

		cell = sheet.getRow(numberRow-1).getCell(2);
		cell.setCellValue("(порядковый номер паспорта в журнале регистрации сопроводительных паспортов перевозки отходов производства)");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false, stylesBorder.borderStyleLine));
		cell.getCellStyle().setFont(setFont(workbook,sizeExplanations,IndexedColors.BLACK,false,true));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 3));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 4, 11));
		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("1. Производитель перевозимых отходов производства");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.LEFT,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,(short)10,IndexedColors.BLACK,false,false));

		cell = sheet.getRow(numberRow-1).getCell(4);
		cell.setCellValue(String.valueOf(accompPassp.getDepartmentsShortName(", "))+" ("+accompPassp.getAddress()+")");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,(short)sizePasted,IndexedColors.BLACK,true,false));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 3));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 4, 11));
		cell = sheet.getRow(numberRow-1).getCell(4);
		cell.setCellValue("(наименование юридического лица, его место нахождения и телефон, фамилия, собственное имя,");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,stylesBorder.borderStyleLine));
		cell.getCellStyle().setFont(setFont(workbook,sizeExplanations,IndexedColors.BLACK,false,true));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));
		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue(accompPassp.getCarrierOrganization().getAddress());
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,sizePasted,IndexedColors.BLACK,false,false));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));
		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("отчество (если таковое имеется) индивидуального предпринимателя, его место жительства и телефон)");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,stylesBorder.borderStyleLine));
		cell.getCellStyle().setFont(setFont(workbook,sizeExplanations,IndexedColors.BLACK,false,false));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 6));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 7, 11));

		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("2.  Собственник  перевозимых  отходов  производства (если он не является их производителем)");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.LEFT,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,(short)10,IndexedColors.BLACK,false,false));

		cell = sheet.getRow(numberRow-1).getCell(7);
		cell.setCellValue(accompPassp.getCarrierOrganization().getName());
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,sizePasted,IndexedColors.BLACK,true,false));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 6));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 7, 11));

		cell = sheet.getRow(numberRow-1).getCell(7);
		cell.setCellValue("(наименование юридического лица, его место нахождения");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.LEFT,VerticalAlignment.CENTER,false,stylesBorder.borderStyleLine));
		cell.getCellStyle().setFont(setFont(workbook,sizeExplanations,IndexedColors.BLACK,false,false));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));

		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue(accompPassp.getCarrierOrganization().getAddress());
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,sizePasted,IndexedColors.BLACK,true,false));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));

		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("и телефон, фамилия, собственное имя, отчество (если таковое имеется)");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,stylesBorder.borderStyleLine));
		cell.getCellStyle().setFont(setFont(workbook,sizeExplanations,IndexedColors.BLACK,false,false));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));

		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("физического лица, в том числе индивидуального предпринимателя, его место жительства и телефон)");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,stylesBorder.borderStyleLine));
		cell.getCellStyle().setFont(setFont(workbook,sizeExplanations,IndexedColors.BLACK,false,false));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 3));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 4, 11));
		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("3. Получатель перевозимых отходов производства");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.LEFT,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,(short)10,IndexedColors.BLACK,false,false));

		cell = sheet.getRow(numberRow-1).getCell(4);
		cell.setCellValue(accompPassp.getContract().getOrganization().getName());
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.LEFT,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,sizePasted,IndexedColors.BLACK,true,false));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 3));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 4, 11));

		cell = sheet.getRow(numberRow-1).getCell(4);
		cell.setCellValue("(наименование юридического");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,stylesBorder.borderStyleLine));
		cell.getCellStyle().setFont(setFont(workbook,sizeExplanations,IndexedColors.BLACK,false,true));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));

		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue(accompPassp.getContract().getOrganization().getAddress());
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,sizePasted,IndexedColors.BLACK,true,false));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));

		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("лица, его место нахождения и телефон, фамилия, собственное имя, отчество");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,stylesBorder.borderStyleLine));
		cell.getCellStyle().setFont(setFont(workbook,sizeExplanations,IndexedColors.BLACK,false,true));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));

		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue(accompPassp.getContract().getOrganization().getWasteDestination());
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,sizePasted,IndexedColors.BLACK,true,false));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));

		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("Договор №"+accompPassp.getContract().getNumber()+" от "+dateConverter(accompPassp.getContract().getContractDate(),"."));
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,sizePasted,IndexedColors.BLACK,true,false));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));

		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("(если таковое имеется) физического лица, в том числе индивидуального предпринимателя, его место жительства и телефон)");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,stylesBorder.borderStyleLine));
		cell.getCellStyle().setFont(setFont(workbook,sizeExplanations,IndexedColors.BLACK,false,true));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 2));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 3, 11));

		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("4. Перевозчик отходов производства");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.LEFT,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,(short)10,IndexedColors.BLACK,false,false));

		cell = sheet.getRow(numberRow-1).getCell(3);
		cell.setCellValue(accompPassp.getCarrierOrganization().getName());
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,sizePasted,IndexedColors.BLACK,false,false));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 2));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 3, 11));

		cell = sheet.getRow(numberRow-1).getCell(3);
		cell.setCellValue("(наименование юридического лица, его место нахождения и телефон,");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,stylesBorder.borderStyleLine));
		cell.getCellStyle().setFont(setFont(workbook,sizeExplanations,IndexedColors.BLACK,false,true));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));

		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue(accompPassp.getCarrierOrganization().getAddress());
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,sizePasted,IndexedColors.BLACK,false,true));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));

		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("его место нахождения и телефон,");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,stylesBorder.borderStyleLine));
		cell.getCellStyle().setFont(setFont(workbook,sizeExplanations,IndexedColors.BLACK,false,true));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));

		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("фамилия, собственное имя, отчество (если таковое имеется) индивидуального предпринимателя,");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,stylesBorder.borderStyleLine));
		cell.getCellStyle().setFont(setFont(workbook,sizeExplanations,IndexedColors.BLACK,false,true));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));

		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("его место жительства и телефон, номер и ");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,stylesBorder.borderStyleLine));
		cell.getCellStyle().setFont(setFont(workbook,sizeExplanations,IndexedColors.BLACK,false,true));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));

		cell = sheet.getRow(numberRow-1).getCell(0);
		cell.setCellValue("5. Сведения о транспортном средстве, на котором осуществляется перевозка отходов производства, перевозимых отходах производства и их упаковке ");
		cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.LEFT,VerticalAlignment.CENTER,false,null));
		cell.getCellStyle().setFont(setFont(workbook,(short)10,IndexedColors.BLACK,false,false));


		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 11));

		numberRow = createRow(sheet,numberRow,true,2,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-1, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-1, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-1, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-1, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-1, 4, 4));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-1, 5, 5));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-2, 6, 7));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-1, 8, 8));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-2, 9, 10));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-2, numberRow-1, 11, 11));

		sheet.getRow(numberRow-2).getCell(0).setCellValue("Дата перевозки отходов производства");
		sheet.getRow(numberRow-2).getCell(1).setCellValue("Транспортное средство: марка и регистрационный номер марка и регистрационный номер автомобиля");
		sheet.getRow(numberRow-2).getCell(2).setCellValue("Фамилия, собственное имя, отчество (если таковое имеется) водителя, подпись");
		sheet.getRow(numberRow-2).getCell(3).setCellValue("Код отходов");
		sheet.getRow(numberRow-2).getCell(4).setCellValue("Степень опасности и класс опасности опасных отходов");
		sheet.getRow(numberRow-2).getCell(5).setCellValue("Количество отходов производства, перевозимых рейсом (тонн (штук))");

		sheet.getRow(numberRow-2).getCell(6).setCellValue("Тара (упаковка)");

		sheet.getRow(numberRow-1).getCell(6).setCellValue("наименование");
		sheet.getRow(numberRow-1).getCell(7).setCellValue("общий вес, тонн");
		sheet.getRow(numberRow-2).getCell(8).setCellValue("Вес транспорта (брутто), тонн");

		sheet.getRow(numberRow-2).getCell(9).setCellValue("Отметка о получении отходов производства");

		sheet.getRow(numberRow-1).getCell(9).setCellValue("подпись получателя отходов производства");
		sheet.getRow(numberRow-1).getCell(10).setCellValue("количество полученных отходов производства, тонн (штук)");
		sheet.getRow(numberRow-2).getCell(11).setCellValue("Номер аварийной карточки");

		for (int i =1;i<3; i++){
			for (int j = 0; j<sheet.getRow(numberRow-i).getLastCellNum();j++){
				sheet.getRow(numberRow-i).getCell(j).setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,true,stylesBorder.borderStyleT));
				sheet.getRow(numberRow-i).getCell(j).getCellStyle().setFont(setFont(workbook,(short)9,IndexedColors.BLACK,true,false));
			}
		}

		numberRow = createRow(sheet,numberRow,true,1,12);
		for (int j = 0; j<sheet.getRow(numberRow-1).getLastCellNum();j++){
			sheet.getRow(numberRow-1).getCell(j).setCellValue(j+1);
			sheet.getRow(numberRow-1).getCell(j).setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,true,stylesBorder.borderStyleT));
			sheet.getRow(numberRow-1).getCell(j).getCellStyle().setFont(setFont(workbook,(short)9,IndexedColors.BLACK,true,false));
		}


		numberRow = createRow(sheet,numberRow,true,2,12);

		sheet.getRow(numberRow-2).getCell(0).setCellValue(dateConverter(accompPassp.getTransportationDate(),"/"));
		sheet.getRow(numberRow-2).getCell(1).setCellValue(accompPassp.getCarModel());
		sheet.getRow(numberRow-1).getCell(1).setCellValue(accompPassp.getCarNumber());
		sheet.getRow(numberRow-2).getCell(2).setCellValue(accompPassp.getDriverFio());

		sheet.getRow(numberRow-2).getCell(3).setCellValue(accompPassp.getWastesCodes("\n"));
		sheet.getRow(numberRow-2).getCell(4).setCellValue(accompPassp.getWastesDangerousPow("\n"));
		sheet.getRow(numberRow-2).getCell(5).setCellValue("***");
		sheet.getRow(numberRow-2).getCell(6).setCellValue(accompPassp.getBoxing());
		sheet.getRow(numberRow-2).getCell(7).setCellValue("-");
		sheet.getRow(numberRow-2).getCell(11).setCellValue("-");

		for (int j = 0; j<sheet.getRow(numberRow-1).getLastCellNum();j++){
			if(j!=1) {
				sheet.getRow(numberRow - 1).getCell(j).setCellValue("-");
				sheet.getRow(numberRow - 1).getCell(j).getCellStyle().setFont(setFont(workbook, (short) 12, IndexedColors.BLACK, false, false));
			}
			sheet.getRow(numberRow-1).getCell(j).setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false,stylesBorder.borderStyleT));

		}
		for (int j = 0; j<sheet.getRow(numberRow-2).getLastCellNum();j++){
			sheet.getRow(numberRow - 2).getCell(j).getCellStyle().setFont(setFont(workbook, (short) 12, IndexedColors.BLACK, false, false));
			sheet.getRow(numberRow-2).getCell(j).setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,true,stylesBorder.borderStyleT));

		}

		for (AccompPasspDepartment department: accompPassp.getAccompPasspDepartments()) {
			numberRow = createRow(sheet, numberRow, true, 2, 12);
			sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 0, 2));
			sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 3, 5));
			sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 6, 7));
			sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 9, 11));
			sheet.getRow(numberRow - 1).getCell(0).setCellValue("Собственник отходов производства");
			sheet.getRow(numberRow-1).getCell(3).setCellValue(department.getDepartment().getChiefPosition());
			sheet.getRow(numberRow-1).getCell(9).setCellValue(department.getDepartment().getChiefFio());
			for (int j = 0; j < sheet.getRow(numberRow - 1).getLastCellNum(); j++) {
				sheet.getRow(numberRow - 1).getCell(j).setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
				sheet.getRow(numberRow - 1).getCell(j).getCellStyle().setFont(setFont(workbook, (short) 12, IndexedColors.BLACK, false, false));
			}

			numberRow = createRow(sheet, numberRow, true, 1, 12);
			sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 0, 2));
			sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 3, 5));
			sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 6, 7));
			sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 9, 11));

			sheet.getRow(numberRow - 1).getCell(6).setCellValue("(подпись)");
			sheet.getRow(numberRow - 1).getCell(9).setCellValue("(инициалы, фамилия)");
			for (int j = 0; j < sheet.getRow(numberRow - 1).getLastCellNum(); j++) {
				sheet.getRow(numberRow - 1).getCell(j).setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
				sheet.getRow(numberRow - 1).getCell(j).getCellStyle().setFont(setFont(workbook, (short) 10, IndexedColors.BLACK, false, true));
			}
			numberRow = createRow(sheet, numberRow, true, 1, 12);
		}
//		CellRangeAddress addresses = new CellRangeAddress(numberRow-1,numberRow-1,3,7);
//		RegionUtil.setBorderTop(BorderStyle.THIN,addresses,sheet);
//		addresses = new CellRangeAddress(numberRow-1,numberRow-1,9,11);
//		RegionUtil.setBorderTop(BorderStyle.THIN,addresses,sheet);

		numberRow = createRow(sheet,numberRow,true,1,12);
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 2));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 3, 7));
		sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 9, 11));
		sheet.getRow(numberRow-1).getCell(0).setCellValue("Перевозчик отходов производства");
		sheet.getRow(numberRow-1).getCell(3).setCellValue(accompPassp.getCarrierOrganization().getName());
		for (int j =0;j<sheet.getRow(numberRow-1).getLastCellNum();j++) {
			sheet.getRow(numberRow - 1).getCell(j).setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
			sheet.getRow(numberRow - 1).getCell(j).getCellStyle().setFont(setFont(workbook, (short) 12, IndexedColors.BLACK, false, false));
		}
//		addresses = new CellRangeAddress(numberRow-1,numberRow-1,3,7);
//		RegionUtil.setBorderBottom(BorderStyle.THIN,addresses,sheet);
//		addresses = new CellRangeAddress(numberRow-1,numberRow-1,9,11);
//		RegionUtil.setBorderBottom(BorderStyle.THIN,addresses,sheet);


//		Integer[] rows = {5,7,9,11,13,15,17,19,21,23,25,27,29};
//		Integer[] first ={2,4,0,7,0,0,4,0,0,0,3,0,0,0};
//		Integer[] last = {11,11,11,11,11,11,11,11,11,11,11,11,11,11};

//		for (int i =1;i<3; i++){
//			for (int j = 0; j<sheet.getRow(numberRow-i).getLastCellNum();j++){
//				sheet.getRow(numberRow-i).getCell(j).setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,true,stylesBorder.borderStyleT));
//				sheet.getRow(numberRow-i).getCell(j).getCellStyle().setFont(setFont(workbook,(short)9,IndexedColors.BLACK,true,false));
//			}
//		}

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

	class Months{

		List<String> months;

		public Months() {
			months = new ArrayList<>();
			months.add("Январь");
			months.add("Февраль");
			months.add("Март");
			months.add("Апрель");
			months.add("Май");
			months.add("Июнь");
			months.add("Июль");
			months.add("Август");
			months.add("Сентябрь");
			months.add("Октябрь");
			months.add("Ноябрь");
			months.add("Декабрь");
		}
	}

	@Override
	public String createAccompPasspJournalReport(int year) throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook();
		StylesBorder stylesBorder = new StylesBorder(workbook);
		Months months = new Months();
		for (int ii=1; ii<12;ii++) {
			String month = "";
			if(ii<10)
			{
				month="0"+String.valueOf(ii);
			}
			else
			{
				month = String.valueOf(ii);
			}
			List<AccompPasspJournalDto> accompPasspJournalDtos = accompPasspService.getAllByYearAndMonth(String.valueOf(year), month);
			if(accompPasspJournalDtos.stream().count()>0) {
				XSSFSheet sheet = workbook.createSheet(months.months.get(ii-1));
				XSSFCell cell;
				//Размеры колонок
				sheet.setColumnWidth(0, 2740);
				sheet.setColumnWidth(1, 2707);
				sheet.setColumnWidth(2, 3460);
				sheet.setColumnWidth(3, 2123);
				sheet.setColumnWidth(4, 2397);
				sheet.setColumnWidth(5, 2877);
				sheet.setColumnWidth(6, 1967);
				sheet.setColumnWidth(7, 1885);
				sheet.setColumnWidth(8, 2060);
				sheet.setColumnWidth(9, 4247);
				sheet.setColumnWidth(10, 2671);

				Integer numberRow = 0;
				numberRow = createRow(sheet, numberRow, true, 1, 11);
				sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 0, 10));
				cell = sheet.getRow(numberRow - 1).getCell(0);
				cell.setCellValue("Журнал");
				cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
				cell.getCellStyle().setFont(setFont(workbook, (short) 11, IndexedColors.BLACK, true, false));

				numberRow = createRow(sheet, numberRow, true, 2, 11);
				sheet.addMergedRegion(new CellRangeAddress(numberRow - 2, numberRow - 2, 0, 10));
				sheet.addMergedRegion(new CellRangeAddress(numberRow - 1, numberRow - 1, 0, 10));
				cell = sheet.getRow(numberRow - 2).getCell(0);
				cell.setCellValue("регистрации сопроводительных паспортов перевозки отходов производства");
				cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, null));
				cell.getCellStyle().setFont(setFont(workbook, (short) 11, IndexedColors.BLACK, true, false));

				numberRow = createRow(sheet, numberRow, true, 2, 11);
				for (int i = 0; i < 6; i++) {
					sheet.addMergedRegion(new CellRangeAddress(numberRow - 2, numberRow - 1, i, i));
				}
				sheet.addMergedRegion(new CellRangeAddress(numberRow - 2, numberRow - 2, 6, 10));
				cell = sheet.getRow(numberRow - 2).getCell(0);
				cell.setCellValue("Регистра-ционный номер сопроводи-тельного паспорта");
				cell = sheet.getRow(numberRow - 2).getCell(1);
				cell.setCellValue("Дата перевозки отходов производства");
				cell = sheet.getRow(numberRow - 2).getCell(2);
				cell.setCellValue("Перевозчик отходов производства, номер и дата договора на передачу отходов производства");
				cell = sheet.getRow(numberRow - 2).getCell(3);
				cell.setCellValue("Получатель отходов производства");
				cell = sheet.getRow(numberRow - 2).getCell(4);
				cell.setCellValue("Код отходов");
				cell = sheet.getRow(numberRow - 2).getCell(5);
				cell.setCellValue("Класс опасности");
				cell = sheet.getRow(numberRow - 2).getCell(6);
				cell.setCellValue("Передано отходов производства");
				cell = sheet.getRow(numberRow - 1).getCell(6);
				cell.setCellValue("на подготовку, тонн (штук)");
				cell = sheet.getRow(numberRow - 1).getCell(7);
				cell.setCellValue("на использование, тонн (штук)");
				cell = sheet.getRow(numberRow - 1).getCell(8);
				cell.setCellValue("на обезврежи-вание, тонн (штук)");
				cell = sheet.getRow(numberRow - 1).getCell(9);
				cell.setCellValue("на захоронение, тонн (штук)");
				cell = sheet.getRow(numberRow - 1).getCell(10);
				cell.setCellValue("на хранение, тонн (штук)");

				for (int i = 1; i < 3; i++) {
					for (int j = 0; j < sheet.getRow(numberRow - i).getLastCellNum(); j++) {
						sheet.getRow(numberRow - i).getCell(j).setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, stylesBorder.borderStyleT));
						sheet.getRow(numberRow - i).getCell(j).getCellStyle().setFont(setFont(workbook, (short) 8, IndexedColors.BLACK, true, false));
					}
				}
				numberRow = createRow(sheet, numberRow, true, 1, 11);
				for (int i = 0; i < sheet.getRow(numberRow - 1).getLastCellNum(); i++) {
					cell = sheet.getRow(numberRow - 1).getCell(i);
					cell.setCellValue(i + 1);
					cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, stylesBorder.borderStyleT));
					cell.getCellStyle().setFont(setFont(workbook, (short) 8, IndexedColors.BLACK, true, false));
				}

				for (AccompPasspJournalDto accompPasspJournalDto : accompPasspJournalDtos) {
					for (AccompPasspWasteDto dto : accompPasspJournalDto.getAccompPasspWasteDtoList()) {
						numberRow = createRow(sheet, numberRow, true, 2, 11);
						sheet.addMergedRegion(new CellRangeAddress(numberRow - 2, numberRow - 1, 0, 0));
						cell = sheet.getRow(numberRow - 2).getCell(0);
						cell.setCellValue(accompPasspJournalDto.getAccompPasspNumber());

						cell = sheet.getRow(numberRow - 2).getCell(1);
						cell.setCellValue(accompPasspJournalDto.getTransportationDate());

						cell = sheet.getRow(numberRow - 2).getCell(2);
						cell.setCellValue(accompPasspJournalDto.getDepartmentsShortName());

						cell = sheet.getRow(numberRow - 1).getCell(2);
						cell.setCellValue("Договор №" + accompPasspJournalDto.getContractNumber() + " от " +  accompPasspJournalDto.getContractDate());

						cell = sheet.getRow(numberRow - 2).getCell(3);
						cell.setCellValue(accompPasspJournalDto.getRecipientOrganizationName());

						cell = sheet.getRow(numberRow - 2).getCell(4);
						cell.setCellValue(dto.getWasteTypeId().getCode());

						cell = sheet.getRow(numberRow - 2).getCell(5);
						cell.setCellValue(dto.getWasteTypeId().getDangerousClassName());

						if (dto.getWasteWeight() != null && dto.getGoal() != null) {
							switch (dto.getGoal().getName()) {
								case "Подготовку": {
									cell = sheet.getRow(numberRow - 2).getCell(6);
									break;
								}
								case "Использование": {
									cell = sheet.getRow(numberRow - 2).getCell(7);
									break;
								}
								case "Обезвреживание": {
									cell = sheet.getRow(numberRow - 2).getCell(8);
									break;
								}
								case "Захоронение": {
									cell = sheet.getRow(numberRow - 2).getCell(9);
									break;
								}
								case "Хранение": {
									cell = sheet.getRow(numberRow - 2).getCell(10);
									break;
								}
								default: {
									cell = sheet.getRow(numberRow - 2).getCell(10);
									break;
								}
							}
							cell.setCellValue(dto.getWasteWeight());
							for (int i = 6; i < sheet.getRow(numberRow - 2).getLastCellNum(); i++) {
								cell = sheet.getRow(numberRow - 2).getCell(i);
								if (cell.getNumericCellValue() == 0.0) {
									cell.setCellValue("-");
								}
							}
						} else {
							sheet.addMergedRegion(new CellRangeAddress(numberRow - 2, numberRow - 2, 6, 10));
							sheet.getRow(numberRow - 2).getCell(6).setCellValue("Вывоз не производился");
						}

						for (int i = 0; i < sheet.getRow(numberRow - 2).getLastCellNum(); i++) {
							cell = sheet.getRow(numberRow - 2).getCell(i);
							cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, stylesBorder.borderStyleT));
							cell.getCellStyle().setFont(setFont(workbook, (short) 10, IndexedColors.BLACK, false, false));
						}

						for (int i = 0; i < sheet.getRow(numberRow - 1).getLastCellNum(); i++) {
							cell = sheet.getRow(numberRow - 1).getCell(i);
							cell.setCellStyle(setCellAlignmeng(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, stylesBorder.borderStyleT));
							cell.getCellStyle().setFont(setFont(workbook, (short) 10, IndexedColors.BLACK, false, false));
						}

						cell = sheet.getRow(numberRow - 2).getCell(0);
						cell.getCellStyle().setFont(setFont(workbook, (short) 14, IndexedColors.BLACK, true, false));
					}
				}
			}
		}
		String reportFile = "D:\\Lagvinovich\\Проекты\\test.xls";

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


	private int createRow(XSSFSheet sheet,int numRow, boolean createCells, int countIterations, int countCol){
		XSSFRow newRow = sheet.createRow(numRow);
		numRow++;
		if(createCells) {
			createCells(newRow, countCol);
		}
		return countIterations>1?createRow(sheet,numRow,true,countIterations-1,countCol):numRow;
	}

	private XSSFCellStyle setCellAlignmeng(XSSFWorkbook wb,HorizontalAlignment alignmentH,VerticalAlignment alignmentV, boolean wrap, XSSFCellStyle borderstyle){
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

	private XSSFFont setFont(XSSFWorkbook workbook,Short HeightInPoints, IndexedColors color, boolean bold, boolean italic){
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
