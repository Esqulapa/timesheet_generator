package pl.jarekzegzula;

import org.apache.commons.codec.Encoder;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import pl.jarekzegzula.DateTypes.Day;
import pl.jarekzegzula.DateTypes.DaysOfMonth;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

public class TimeSheetCreator {



    private final short cellHeight = 378;
    private final int DATE_COLUMN = 0;
    private final int DAY_OF_WEEK_COLUMN = 1;
    private final int NUMBER_OF_HOURS_COLUMN = 2;
    private final int IS_WORKING_DAY_COLUMN = 3;

    public HSSFWorkbook createTimeSheet(Map<YearMonth, DaysOfMonth> daysByYearMonth,Locale locale) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();

        CellStyle borderStyle = dayStyle(workbook);
        CellStyle headlinesStyle = headlinesStyle(workbook);
        CellStyle cellStyleForTrueValue = cellStyleForTrueValue(workbook);
        CellStyle cellStyleForFalseValue = cellStyleForFalseValue(workbook);

        for (Map.Entry<YearMonth, DaysOfMonth> entry : daysByYearMonth.entrySet()) {

            DaysOfMonth daysOfMonth = entry.getValue();

            HSSFSheet sheet = workbook.createSheet(String.format("%s", entry.getKey()));

            setColumnWidth(sheet);

            int rowCount = 0;

            HSSFRow headlines = sheet.createRow(rowCount++);

            setColumnHeadlines(headlines,locale);

            setRowsCellStyle(headlines, headlinesStyle);

            rowCount = createRowsForEachDayInMonth(daysOfMonth, sheet, rowCount, borderStyle, cellStyleForTrueValue, cellStyleForFalseValue);


            workingTimeCountingCell(daysOfMonth, sheet, rowCount, borderStyle);


            sheet.getRow(rowCount).setHeight(cellHeight);

        }
        saveAtDesktop(workbook);

        return workbook;

    }

    private CellStyle cellStyleForFalseValue(HSSFWorkbook workbook) {
        CellStyle redCellStyle = workbook.createCellStyle();
        redCellStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
        redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        redCellStyle.setBorderTop(BorderStyle.THIN);
        redCellStyle.setBorderBottom(BorderStyle.THIN);
        redCellStyle.setBorderLeft(BorderStyle.THIN);
        redCellStyle.setBorderRight(BorderStyle.THIN);
        return redCellStyle;
    }

    private CellStyle cellStyleForTrueValue(HSSFWorkbook workbook) {
        CellStyle greenCellStyle = workbook.createCellStyle();
        greenCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        greenCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        greenCellStyle.setBorderTop(BorderStyle.THIN);
        greenCellStyle.setBorderLeft(BorderStyle.THIN);
        greenCellStyle.setBorderRight(BorderStyle.THIN);
        greenCellStyle.setBorderBottom(BorderStyle.THIN);
        return greenCellStyle;
    }

    private int createRowsForEachDayInMonth(DaysOfMonth daysOfMonth, HSSFSheet sheet, int rowCount, CellStyle cellStyle,
                                            CellStyle styleForTrueValue, CellStyle styleForFalseValue) {
        for (Day day : daysOfMonth.days()) {

            Row row = sheet.createRow(rowCount++);

            row.setHeight(cellHeight);

            row.createCell(DATE_COLUMN).setCellValue(day.getDate().toString());

            row.createCell(DAY_OF_WEEK_COLUMN).setCellValue(day.getDayOfWeekInSpecificLanguage());

            row.createCell(NUMBER_OF_HOURS_COLUMN).setCellValue(day.getNumberOfWorkingHours());

            row.createCell(IS_WORKING_DAY_COLUMN).setCellValue(day.isWorkingDay(day.getWorkingDays()));

            Cell cell = row.getCell(IS_WORKING_DAY_COLUMN);

            setRowsCellStyle(row, cellStyle);

            checkCellIfConditionAndPutStyle(styleForTrueValue, styleForFalseValue, cell);

            changeBoolValueToSpecificLanguage(cell,day.getLocale());


        }
        return rowCount;
    }

    private void changeBoolValueToSpecificLanguage(Cell cell, Locale locale) {
        cell.setCellValue(booleanToString(cell.getBooleanCellValue(),locale));
    }

    public String booleanToString(boolean b, Locale locale) {
        if (locale.equals(Locale.forLanguageTag("pl-PL"))) {
            return b ? "TAK" : "NIE";
        }
        else
            return b ? "YES" : "NO";
    }

    private void checkCellIfConditionAndPutStyle(CellStyle styleForTrueValue, CellStyle styleForFalseValue, Cell cell) {
        if (cell.getBooleanCellValue()) {
            cell.setCellStyle(styleForTrueValue);
        } else
            cell.setCellStyle(styleForFalseValue);
    }

    private static CellStyle dayStyle(HSSFWorkbook workbook) {
        CellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setBorderTop(BorderStyle.THIN);
        borderStyle.setBorderBottom(BorderStyle.THIN);
        borderStyle.setBorderLeft(BorderStyle.THIN);
        borderStyle.setBorderRight(BorderStyle.THIN);

        return borderStyle;
    }

    private static CellStyle headlinesStyle(HSSFWorkbook workbook) {
        CellStyle borderStyle = workbook.createCellStyle();

        borderStyle.setBorderTop(BorderStyle.THIN);
        borderStyle.setBorderBottom(BorderStyle.THIN);
        borderStyle.setBorderLeft(BorderStyle.THIN);
        borderStyle.setBorderRight(BorderStyle.THIN);

        borderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        borderStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.index);

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBold(true);
        borderStyle.setFont(font);

        return borderStyle;
    }

    private void workingTimeCountingCell(DaysOfMonth daysOfMonth, HSSFSheet sheet, int rowCount, CellStyle cellStyle) {
        HSSFRow row = sheet.createRow(rowCount);
        row.createCell(2)
                .setCellValue(daysOfMonth
                        .countWorkingDays(daysOfMonth, daysOfMonth.days().get(1).getWorkingDays()));
        setRowsCellStyle(row, cellStyle);

    }

    private static void saveAtDesktop(HSSFWorkbook workbook) {
        String desktopPath = System.getProperty("user.home") + "/Desktop/";
        String filePath = desktopPath + "timesheet.xlsx";

        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("File saved successfully in: \n " + filePath );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setColumnHeadlines(HSSFRow row,Locale locale) {
        if (locale.equals(Locale.forLanguageTag("pl-PL"))) {

            headlinesInPolish(row);

        }
        else {
            headlinesInEnglish(row);
        }



    }

    private void headlinesInPolish(HSSFRow row) {
        row.setHeight(cellHeight);

        row.createCell(DATE_COLUMN).setCellValue("DATA");

        row.createCell(DAY_OF_WEEK_COLUMN).setCellValue("DZIEN");

        row.createCell(NUMBER_OF_HOURS_COLUMN).setCellValue("GODZINY");

        row.createCell(IS_WORKING_DAY_COLUMN).setCellValue("DZIEN PRACUJACY");

    }

    private void headlinesInEnglish(HSSFRow row) {
        row.setHeight(cellHeight);

        row.createCell(DATE_COLUMN).setCellValue("DATE");

        row.createCell(DAY_OF_WEEK_COLUMN).setCellValue("DAY OF WEEK");

        row.createCell(NUMBER_OF_HOURS_COLUMN).setCellValue("HOURS");

        row.createCell(IS_WORKING_DAY_COLUMN).setCellValue("WORKING DAY");

    }

    private void setColumnWidth(HSSFSheet sheet) {
        sheet.setColumnWidth(DATE_COLUMN, 2700);
        sheet.setColumnWidth(DAY_OF_WEEK_COLUMN, 4000);
        sheet.setColumnWidth(NUMBER_OF_HOURS_COLUMN, 3500);
        sheet.setColumnWidth(IS_WORKING_DAY_COLUMN, 3500);
    }

    private void setRowsCellStyle(Row row, CellStyle cellStyle) {

        for (Cell cell : row) {
            if (row.cellIterator().hasNext()) {
                cell.setCellStyle(cellStyle);
            }

        }

    }


}
