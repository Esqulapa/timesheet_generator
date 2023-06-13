package pl.jarekzegzula;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelOperation {
    public static void main(String[] args) throws IOException {


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet");

        ArrayList<Object[]> empdata = new ArrayList<>();
        empdata.add( new Object[]{"EmpID","Name","Job"});
        empdata.add( new Object[]{101,"Mario","Worker"});
        empdata.add( new Object[]{102,"Scott","Manager"});
        empdata.add( new Object[]{103,"Roger","Analyst"});


        int rowCount = 0;
// Tutaj przechowuje pierwszy row Object[]{"EmpID","Name","Job"}

        for (var emp :empdata) {
            HSSFRow row = sheet.createRow(rowCount++);
            int cellCount = 0;

            for (Object value: emp) {
                HSSFCell cell = row.createCell(cellCount++);
                if (value instanceof String)
                    cell.setCellValue((String) value);
                if (value instanceof Integer)
                    cell.setCellValue((Integer) value);
                if (value instanceof Boolean)
                    cell.setCellValue((Boolean) value);

            }

        }
        String filepath=".\\datafiles\\sheet.xls";
        FileOutputStream outputStream = new FileOutputStream(filepath);
        workbook.write(outputStream);

        outputStream.close();

        System.out.println("sheet.xlxs  file has been written succesfully");


    }
}
