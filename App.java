//automatic case creation program (ACCP);
package com.bee.am;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
public class App {
    private static final Logger log = Logger.getLogger(App.class);
   protected HashMap<String, String> CaseStatMap =new HashMap<String,String>();
   protected void setCaseStatMap(String key,String value){
       CaseStatMap.put(key,value);
   }
    App()
    {
        File mapFile = new File(Fileutils.ftpResources.getString("case_map_file"));
        loadExcelLines(mapFile);
    }
    private void loadExcelLines(File fileName)
    {
        // Used the LinkedHashMap and LikedList to maintain the order
        HashMap<String, LinkedHashMap<Integer, List>> outerMap = new LinkedHashMap<String, LinkedHashMap<Integer, List>>();
        LinkedHashMap<String, List> hashMap = new LinkedHashMap<String, List>();
        String sheetName = null;
        // Create an ArrayList to store the data read from excel sheet.
        // List sheetData = new ArrayList();
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(fileName);
            // Create an excel workbook from the file system
            XSSFWorkbook workBook = new XSSFWorkbook(fis);
            // Get the first sheet on the workbook.
            XSSFSheet sheet = workBook.getSheetAt(0);
            // XSSFSheet sheet = workBook.getSheetAt(0);
            sheetName = workBook.getSheetName(0);
            Iterator rows = sheet.rowIterator();
            String prefix ="";
            while (rows.hasNext())
            {
                XSSFRow row = (XSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                List data = new LinkedList();
                while (cells.hasNext())
                {
                    XSSFCell cell = (XSSFCell) cells.next();
                    data.add(cell);
                }

                hashMap.put(row.getCell(0).getRawValue(), data);
                // sheetData.add(data);
            }
            //  outerMap.put(sheetName, hashMap);
            // hashMap = new LinkedHashMap<Integer, List>();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        CaseMap= hashMap;
    }
    public void startCProcess(String act)
    {
        log.info("START " + act + " PROCESS !!!");
        CaseProcess cp = new CaseProcess();
        cp.get_Act_for_case(act);
    }
    public static HashMap gettestMap() { return CaseMap;}
    public static String getcasename (Object key , String event)
    {
        String case_name = null;
        LinkedList qname = (LinkedList)CaseMap.get(key);
        switch (event) {
            case "DEL":
                case_name=qname.get(4).toString(); //title delete;
                break;
            case "SUS": case_name=qname.get(5).toString(); //title cansel;
                break;
            case "RES":case_name=qname.get(6).toString();//title resume;
                break;
            default:
                break;
        }
        return UnicodeToARMCyr.UToARMCyr((case_name));
    }

    /* for check case name
    public  String testCaseName (Object key , String event)
    {
        String case_name = null;
        LinkedList qname = (LinkedList)CaseMap.get(key);
        switch (event) {
            case "DEL":
                case_name=qname.get(4).toString(); //title delete;
                break;
            case "SUS": case_name=qname.get(5).toString(); //title cansel;
                break;
            case "RES":case_name=qname.get(6).toString();//title resume;
                break;
            default:
                break;
        }
        return UnicodeToARMCyr.UToARMCyr((case_name));
    }
    */

    public void _flushErrorCases(){CaseProcess cp = new CaseProcess(); cp._collectErrorCases();}
    private static HashMap CaseMap;
    public static void main(String[] as) {
       // App pp = new App();
        System.out.println("!!! Start Auto Provis !!!");
        // pp.startCProcess("DEL"); //TasksGenerator Task = new TasksGenerator();
   //System.out.println("case name =" + pp.gettest( "74312","DEL"));
       TasksGenerator Task = new TasksGenerator();
    }
}
