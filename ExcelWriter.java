package com.bee.am;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

   public class ExcelWriter {
    ExcelWriter() {
        initTimestamp();
        _outPath=Fileutils.ftpResources.getString("localFolder");
        _errorFolder = Fileutils.ftpResources.getString("errorFolder");
    }
   private String ConvertDate(String date) throws ParseException {
       DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
       DateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date ndate = parser.parse(date);
       String output = formatter.format(ndate);
       return output;
   }
   public String SaveToFile (Map.Entry<String, List<Subscriber>> entry, String action) throws IOException, InvalidFormatException, ParseException {
        log.info("Start to save excel file, action is "+action);
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
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
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
       String prf ="";
        for (Subscriber tab : entry.getValue()) {
            //String employee= (String)tab1.get(0)[3];
            String creation_date = ConvertDate((tab.getSys_creation_date().toString()));
            String effective_date = ConvertDate(tab.getEffective_date().toString());
            Row row = sheet.createRow(rowNum++);
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(tab.getSubscriber_no());
          //  System.out.println("getSubscriber_no = "+ tab.getSubscriber_no()+"||"+ "getSubscriber = " + tab.getSubscriber()+"||"+ "getAddress = "+ tab.getAddress()+ "||" + "getAdditional_info = "+tab.getAdditional_info() +"||"+ "SOC = "+tab.getSoc()  );
           Cell cell1 = row.createCell(1);
             cell1.setCellValue(creation_date);
             cell1.setCellStyle(fdat);
           Cell cell2 = row.createCell(2);
             cell2.setCellValue(tab.getSubscriber());
             cell2.setCellStyle(SubscriberCellStyle);
           Cell cell3 = row.createCell(3);
             cell3.setCellValue(tab.getAddress());
             cell3.setCellStyle(SubscriberCellStyle);
           Cell cell4 = row.createCell(4);
            cell4.setCellValue(effective_date);
            cell4.setCellStyle(fdat);
           Cell cell5 = row.createCell(5);
            cell5.setCellValue(tab.getAdditional_info());
           Cell cell6 = row.createCell(6);
            cell6.setCellValue(tab.getSoc());
           Cell cell7 = row.createCell(7);
            cell7.setCellValue(tab.getLogical_dvc_id());
            for(int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            FileOutputStream fileOut = new FileOutputStream(_outPath+action+"_"+entry.getKey()+"_"+timestamp+".xls");
            workbook.write(fileOut);
            fileOut.close();
            // Closing the workbook
            workbook.close();
        }
    return _outPath + action+"_"+entry.getKey()+"_"+timestamp+".xls";
    }
   public void _saveErrors( List<Object[]> result) throws IOException, ParseException{
    log.info("Errors collecting is started... ");
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("Errors");
    CreationHelper createHelper = workbook.getCreationHelper();
    Font headerFont = workbook.createFont();
    headerFont.setBold(true);
    headerFont.setFontHeightInPoints((short) 14);
    headerFont.setColor(IndexedColors.RED.getIndex());
    CellStyle headerCellStyle = workbook.createCellStyle();
    headerCellStyle.setFont(headerFont);
    CellStyle fdat =workbook.createCellStyle();
    fdat.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
    fdat.setAlignment(HorizontalAlignment.CENTER);
    Font armcyr = workbook.createFont();
    armcyr.setFontName("Arial Armenian Cyr");
    CellStyle SubscriberCellStyle = workbook.createCellStyle();
    SubscriberCellStyle.setFont(armcyr);
    // Create a Row
    Row headerRow = sheet.createRow(0);
    // Create cells
    for(int i = 0; i < columnsError.length; i++) {
        Cell cell = headerRow.createCell(i);
        cell.setCellValue(columnsError[i]);
        cell.setCellStyle(headerCellStyle);
    }
    int rowNum = 1;
    for (Object[] tab : result) {
        //String employee= (String)tab1.get(0)[3];
        System.out.println("tab[0].toString() =  " +tab[0].toString());
        String creation_date = ConvertDate((tab[3].toString()));
        Row row = sheet.createRow(rowNum++);
        Cell cell0 = row.createCell(0);
        cell0.setCellValue(tab[0].toString());
        Cell cell1 = row.createCell(1);
        cell1.setCellValue(tab[1].toString());
        Cell cell2 = row.createCell(2);
        cell2.setCellStyle(SubscriberCellStyle);
        cell2.setCellValue(tab[2].toString());
        Cell cell3 = row.createCell(3);
        cell3.setCellValue(creation_date);
        cell3.setCellStyle(fdat);
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    FileOutputStream fileOut = new FileOutputStream(_errorFolder + "_ErrorTransactions_" + timestamp + ".xlsx");
    workbook.write(fileOut);
    fileOut.close();
    workbook.close();
log.info("Errors saved to file " + _errorFolder + "_ErrorTransactions_" + timestamp + ".xlsx" );

    // Closing the workbook

}
  private void initTimestamp()
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        timestamp = df.format(new Date());
    }

   public  String get_outPath()
{
    return _outPath;
}
private String timestamp;
private static String[] columns = {"SUBSCRIBER_NO", "SYS_CREATION_DATE", "SUBSCRIBER", "ADDRESS", "EFFECTIVE_DATE", "ADDITIONAL_INFO", "SOC", "LOGICAL_DVC_ID"};
private static String[] columnsError = {"GROUP_TRX_SEQ_NO","TRX_STATUS","ERROR_TEXT","SYS_CREATION_DATE"};
private  String _outPath;
private String _errorFolder;
private static final Logger log = Logger.getLogger(ExcelWriter.class);
}
