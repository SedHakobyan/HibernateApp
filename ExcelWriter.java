package com.bee.am;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelWriter {
    private static String[] columns = {"SUBSCRIBER_NO", "SYS_CREATION_DATE", "SUBSCRIBER", "ADDRESS", "EFFECTIVE_DATE", "ADDITIONAL_INFO", "SOC", "LOGICAL_DVC_ID"};
   private String ConvertDate(String date) throws ParseException {
       DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
       DateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date ndate = parser.parse(date);
       String output = formatter.format(ndate);
       return output;

   }




    public void savetoExcel_one() throws IOException, ParseException {


    Workbook workbook = new HSSFWorkbook();// for generating `.xls` file
    CreationHelper createHelper = workbook.getCreationHelper();
    Sheet sheet = workbook.createSheet("Case");
    Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short)14);
        headerFont.setColor(IndexedColors.YELLOW.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

       CellStyle fdat2 =workbook.createCellStyle();
       fdat2.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
       fdat2.setAlignment(HorizontalAlignment.CENTER);

        Row headerRow = sheet.createRow(0);
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);

        }
        String tox="2015-04-30 09:31:56.0";
        String tox2="2019-12-30 09:31:66.0";
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        DateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = parser.parse(tox);
        String output = formatter.format(date);
       System.out.println("tox = " + output);

       Row row1 = sheet.createRow(2);
        Cell cell1 = row1.createCell(1);
        cell1.setCellValue(output);
        cell1.setCellStyle(fdat2);

        date = parser.parse(tox2);
        output = formatter.format(date);
        Cell cell2 = row1.createCell(4);
        cell2.setCellValue(output);
        cell2.setCellStyle(fdat2);

       for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
       }
        FileOutputStream fileOut = new FileOutputStream("D:\\poi-generated-file_one.xls");
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();
}
    /*public void SaveToFile (Map<String, List<Subscriber>> entry) throws IOException, InvalidFormatException, ParseException {
        Workbook workbook = new HSSFWorkbook();// for generating `.xls` file
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Case");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.YELLOW.getIndex());
        Font armcyr = workbook.createFont();
       armcyr.setFontHeightInPoints((short) 10);
       armcyr.setFontName("Arial Armenian Cyr");
        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        DataFormat dataFormat = workbook.createDataFormat();
        CellStyle fdat =workbook.createCellStyle();
        fdat.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        fdat.setAlignment(HorizontalAlignment.CENTER);
        CellStyle SubscriberCellStyle = workbook.createCellStyle();
        SubscriberCellStyle.setFont(armcyr);
        //SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        // Create a Row
        Row headerRow = sheet.createRow(0);
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        int rowNum = 1;
        for (Object[] tab : result) {
            //String employee= (String)tab1.get(0)[3];
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue((String)tab[0]);
            String tox = ConvertDate(((Timestamp)tab[1]).toString());
            System.out.println("chor timestamp = "+ tox );
            Cell cell1 = row.createCell(1);
            cell1.setCellValue(tox);
            cell1.setCellStyle(fdat);
            Cell cell2 = row.createCell(2);
            cell2.setCellValue(tab[3].toString());
            cell2.setCellStyle(SubscriberCellStyle);
            String data_creat = (String)tab[0];
            //   popo =data_creat;
            for(int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            FileOutputStream fileOut = new FileOutputStream("D:\\poi-generated-file.xls");
            workbook.write(fileOut);
            fileOut.close();

            // Closing the workbook
            workbook.close();

        }

    }
*/



}
