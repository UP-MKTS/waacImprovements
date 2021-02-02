package com.mkts.waac.services.impl;

import com.mkts.waac.models.AccompPasspDepartment;
import com.mkts.waac.models.Pod9OwnWaste;
import com.mkts.waac.services.Pod9OwnWasteService;
import com.mkts.waac.services.Pod9ReportService;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Predicate;

import javax.persistence.Access;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.cglib.core.CollectionUtils.filter;

public class Pod9ReportServiceImpl implements Pod9ReportService {

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
            borderStyleLine.setBorderBottom(BorderStyle.NONE);
            borderStyleLine.setBorderLeft(BorderStyle.NONE);
            borderStyleLine.setBorderRight(BorderStyle.NONE);
            borderStyleLine.setBorderTop(BorderStyle.NONE);

        }


    }

    @Override
    public String download(Integer departmentId) {

        XSSFWorkbook workbook =  new XSSFWorkbook();
        Pod9ReportServiceImpl.StylesBorder stylesBorder = new Pod9ReportServiceImpl.StylesBorder(workbook);

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

        XSSFSheet sheet = workbook.createSheet("Сопроводительный");
        Integer numberRow = 0;
        numberRow = createRow(sheet,numberRow,true,2,11);
        sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 0, 6));
        sheet.addMergedRegion(new CellRangeAddress(numberRow-1, numberRow-1, 9, 10));

        XSSFCell cell = sheet.getRow(numberRow-1).getCell(0);
        cell.setCellValue("НАЗВАНИЕ ОТХОДА");
        cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false, stylesBorder.borderStyleLine));
        cell.getCellStyle().setFont(setFont(workbook,(short)16,IndexedColors.BLACK,true,false));

        cell = sheet.getRow(numberRow-1).getCell(8);
        cell.setCellValue("КОД ОТХОДА");
        cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false, stylesBorder.borderStyleLine));
        cell.getCellStyle().setFont(setFont(workbook,(short)16,IndexedColors.BLACK,true,false));

        cell = sheet.getRow(numberRow-1).getCell(9);
        cell.setCellValue("КЛАСС ОПАСНОСТИ");
        cell.setCellStyle(setCellAlignmeng(workbook,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,false, stylesBorder.borderStyleLine));
        cell.getCellStyle().setFont(setFont(workbook,(short)16,IndexedColors.BLACK,true,false));






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
