package com.bee.am;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

   public class CaseProcess {

    static   String getSubscr_noPrefix(Subscriber s){

        if(s.getSubscriber_no().trim().startsWith(YEREVAN_PREFIX)) return s.getSubscriber_no().substring(0, 6);
        return s.getSubscriber_no().substring(0, 5);
    }
    protected void get_Act_for_case (String act)
{
    HibernateUtil hib = new HibernateUtil();
    AddMap(hib._proccessCan_Res_Sus(act));
    OpenCaseWithFile(_myMap,act);
}
    private void _case(Session session,String action,String que_name, String filename) {
            log.info("create case for "+action);
            CRMcase crmcase = new CRMcase();
            crmcase._createCase(session,action,que_name,filename);

    }

    public Map<String, List<Subscriber>> getmyMap ()
    {
        return _myMap;
    }
       private void OpenCaseWithFile(Map<String, List<Subscriber>> myMap, String action) {
        if (!getmyMap().isEmpty()) {
            log.info("Case create process is starting...");
            Iterator<Map.Entry<String, List<Subscriber>>> entries = myMap.entrySet().iterator();
            ExcelWriter _toexcel = new ExcelWriter();
            Session session = null;
            try {
                Fileutils ftpobj = new Fileutils();
                HibernateUtil hib = new HibernateUtil();
                session = hib.getSessionFromcnf("hibernate-crm.cfg.xml");
                while (entries.hasNext()) {
                    Map.Entry<String, List<Subscriber>> entry = entries.next();
                    try {
                        String filename = _toexcel.SaveToFile(entry, action);
                        ftpobj._fileAttachmentProcess(new File(filename));
                        log.info("sleeping for 20 secs before create case...");
                        Thread.sleep(20000);
                        _case(session,action,App.getcasename(entry.getKey(),action),filename);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InvalidFormatException e) {
                        e.printStackTrace();
                        log.error("Failed to create case..." + e);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        log.error("Failed in process..." + e);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        log.error("Interrupted error..." + e);
                    }
                }
                _clenup(_toexcel.get_outPath());
            }
               catch(Exception e)
               {
                   e.printStackTrace();
                   log.error("process is crashed..." + e);
               }
            finally {
                if (session != null && session.isOpen()) {
                    session.clear();
                    session.close();
                    log.info("Case create process is finish !!!");
                }
            }
        } else {
                 log.info("NOT NEED TO CREATE CASE, _myMap is Empty!!!");
        }
    }
    private void   AddMap(List<Subscriber> sb) {
        _myMap = sb.stream()
                .collect(Collectors.groupingBy(CaseProcess::getSubscr_noPrefix));
        log.info("Fill list for case!!!");
    }
    private void _clenup(String dirname)
    {
        File file = new File(dirname);
        String[] myFiles;
        if(file.isDirectory()){
            myFiles = file.list();
            for (int i=0; i<myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }
        log.info("Cleaned all file from root directory ...");
    }
    protected void _collectErrorCases() {
        HibernateUtil bil = new HibernateUtil();
        ExcelWriter prt = new ExcelWriter();
        Session ss = bil.getSessionFromcnf("hibernate-crm.cfg.xml");
        Transaction transaction = null;
        try {
            LocalDate dat = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
            transaction = ss.beginTransaction();
            List<Object[]> res = ss.createNativeQuery(HibernateUtil.error_LOGSelect).setParameter(1, dat.format(formatter)).list();
            System.out.println("tram tam = ");
            transaction.commit();
            if (res.isEmpty()) log.info("No error case found ...");
            else prt._saveErrors(res);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ss != null) {
                ss.close();
            }
        }
    }
    private static String YEREVAN_PREFIX="7410";
    private Map<String, List<Subscriber>> _myMap;
    private int ev;
    protected static  ResourceBundle ftpResources = ResourceBundle.getBundle("init");
    private static final Logger log = Logger.getLogger(CaseProcess.class);
}