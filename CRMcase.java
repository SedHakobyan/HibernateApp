package com.bee.am;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

public class CRMcase {
 private static String CASE_TYPE1="Шдйрілалар баиір";
 private static String CASE_TYPE2="Лащвавіщ";
 private  long GROUP_TRX_SEQ_NO;
 private static String FIRST_NAME= "Дщдчар";
 private  String QUEUE_NAME;
 private  String TITLE="Љартыо";
 private String CUSTOMER_SEVERITY="ту";
 private static String Select_group_trx_seq_no = "select  PIETRX.CRM_TRANSACTION_LOG_1SQ.nextval  from dual";
 private static String  INSERT_TRANSACTION_LOG = "INSERT INTO PIETRX.CRM_TRANSACTION_LOG  (GROUP_TRX_SEQ_NO,TRX_SEQ_NO,SYS_CREATION_DATE,OPERATOR_ID,APPLICATION_ID,ACTV_CODE,TRX_STATUS,BEN,SUBSCRIBER_NO,TRX_PROCESS) VALUES  (?,1,SYSDATE,0,'ACCP','CAS','N',1,'7499111111','P')";  // Application id = ACCP (automatic case creation program)
 private static String INSERT_CASE_DATA  = "INSERT INTO PIETRX.CRM_CASE_DATA  (GROUP_TRX_SEQ_NO,  TRX_SEQ_NO,  CREATION_TIME, TITLE, CASE_TYPE1, CASE_TYPE2,CASE_TYPE3,CASE_PRIORITY, CUSTOMER_SEVERITY,QUEUE_NAME,AGENT_ID,FIRST_NAME) values (?, 1,  SYSDATE,?,?,?,'None','Regular',?,?,'Robot',?)";
 private static String INSERT_ATTACHMENT_DATA = "INSERT INTO PIETRX.CRM_ATTACHMENT_DATA (GROUP_TRX_SEQ_NO,TRX_SEQ_NO,TITLE,PATH,ATT_TYPE) values  (?,1,?,?,3)";
 private static String PATH =Fileutils.ftpResources.getString("remoteFolder");; //for ATTACHMENT file;
 private static final Logger log = Logger.getLogger("CRMcase.class");
 private String event;
     public void _setMapTableValues(int i, String prefix, LinkedHashMap<String, List> _map)
     {
         QUEUE_NAME= UnicodeToARMCyr.UToARMCyr(_map.get(prefix).get(i).toString());
     }


     private String shortfname(String fname)
     {
         int i = fname.lastIndexOf("/")+1;
         return  fname.substring(i,fname.length());
     }

     public void _createCase( Session session, String act, String que_name, String filename)
     {
         log.info("Starting create case ...");
         switch (act) {
             case "DEL": TITLE="Љартыо"; //title delete;
                 break;
             case "RES": TITLE="Оіаътыо"; //title resume;
                 break;
             case "SUS": TITLE="Арфаштыо"; //title Suspend;
                 break;
             default:
                 break;
         }
         QUEUE_NAME = que_name;
         Transaction transaction = null;
         LocalDate currentDate = LocalDate.now();
         String _pfilenam = PATH + currentDate +"\\"+ shortfname(filename);
    try {
        transaction = session.beginTransaction();
        List<Object> tab1 = session.createNativeQuery(Select_group_trx_seq_no).list();
        GROUP_TRX_SEQ_NO = Long.parseLong(tab1.get(0).toString());
        transaction.commit();
        System.out.println("GROUP_TRX_SEQ_NO =" + GROUP_TRX_SEQ_NO); //????
        transaction = session.beginTransaction();
        session.createNativeQuery(INSERT_TRANSACTION_LOG).setParameter(1, GROUP_TRX_SEQ_NO).executeUpdate();
        transaction.commit();
        transaction = session.beginTransaction();
        session.createNativeQuery(INSERT_CASE_DATA).setParameter(1, GROUP_TRX_SEQ_NO).setParameter(2, TITLE).setParameter(3, CASE_TYPE1).setParameter(4, CASE_TYPE2).setParameter(5, CUSTOMER_SEVERITY).setParameter(6, QUEUE_NAME).setParameter(7, FIRST_NAME).executeUpdate();
        transaction.commit();

        transaction = session.beginTransaction();
        log.info("filename ..... is " + filename +" || que_name = " + que_name);
        session.createNativeQuery(INSERT_ATTACHMENT_DATA).setParameter(1, GROUP_TRX_SEQ_NO).setParameter(2, shortfname(filename)).setParameter(3, _pfilenam).executeUpdate();
        transaction.commit();
    }
    catch (RuntimeException e)
    {
        if (transaction != null) {
            // transaction rollback
            transaction.rollback();
        }
        log.error("Failed to insert data to db crm .. " + e);
    }
    }
}
