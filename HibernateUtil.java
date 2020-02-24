package com.bee.am;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HibernateUtil {
    protected static String DEL = " select distinct(s.subscriber_no),s.SYS_CREATION_DATE,s.sub_status_rsn_code ,"
            + " nd.last_business_name  || ' ' ||rtrim(nd.first_name)as bajanord,"
            + " rtrim(ad.adr_place_type)|| ' ' || rtrim(ad.adr_city) || '  ' ||"
            + " rtrim(ad.adr_primary_ln)|| ' ' ||rtrim(ad.adr_street_type)|| ' ' ||"
            + " rtrim(ad.adr_house_no) || '  ' || rtrim(ad.adr_apt_type)|| ' ' ||ad.adr_apt_nm as hasce,"
            + " s.effective_date ,sf.additional_info , sf.soc ,ds.logical_dvc_id"
            +  "    from amnappo.subscriber s ,amnrefwork.logic_devs ds ,amnappo.service_feature sf ,"
            +   "      amnappo.address_name_link anl,"
            +    "     amnappo.address_data      ad,"
            +     "     amnappo.name_data         nd"

            + " where  trunc(s.EFFECTIVE_DATE)>=trunc(sysdate-16)"
            + " and s.subscriber_no ='7424453566'"
            + " and anl.ban=s.customer_id"
            + " and anl.expiration_date is null"
            + " and anl.link_type = 'T'"
            + " and ad.address_id=anl.address_id"
            + " and nd.name_id=anl.name_id"
            + " and s.customer_id=sf.ban"
            + " and s.subscriber_no=sf.subscriber_no"
            + " and s.sub_status_rsn_code ='CO'"
            + " and  trunc(sf.ftr_expiration_date)>=trunc(sysdate-16)"
            + " and sf.feature_code = 'FLPORT'"
            + " and s.SUB_STATUS_LAST_ACT ='CAN'"
            + " and  (s.product_code='DWLN' or s.product_code='AWLN')"
            + " and to_number(s.subscriber_no) between ds.FROM_NO and ds.TO_NO"
            + " and ds.dvc_tp='ate'";

    protected static String SUS = " select distinct(s.subscriber_no),s.SYS_CREATION_DATE, "
            +"s.sub_status_rsn_code, "
            + "nd.last_business_name  || ' ' ||rtrim(nd.first_name)as bajanord,"
            + "rtrim(ad.adr_place_type)|| ' ' || rtrim(ad.adr_city) || '  ' ||"
            + "rtrim(ad.adr_primary_ln)|| ' ' ||rtrim(ad.adr_street_type)|| ' ' ||"
            + "rtrim(ad.adr_house_no) || '  ' || rtrim(ad.adr_apt_type)|| ' ' ||ad.adr_apt_nm as hasce,"
            + "s.effective_date ,sf.additional_info , sf.soc ,ds.logical_dvc_id"
            +  "   from amnappo.subscriber s ,amnrefwork.logic_devs ds ,amnappo.service_feature sf ,"
            +   "     amnappo.address_name_link anl,"
            +    "    amnappo.address_data      ad,"
            +    "    amnappo.name_data         nd"
            + " where  trunc(s.EFFECTIVE_DATE)>=trunc(sysdate-10)"
            + " and anl.ban=s.customer_id"
            + " and anl.expiration_date is null"
            + " and anl.link_type = 'T'"
            + " and ad.address_id=anl.address_id"
            + " and nd.name_id=anl.name_id"
            + " and s.subscriber_no=sf.subscriber_no"
            + " and s.customer_id=sf.ban"
            + " and sf.feature_code = 'FLPORT'"
            + " and sf.ftr_expiration_date  is NULL"
            + " and s.SUB_STATUS_LAST_ACT = 'SUS'"
            + " and s.sub_status_rsn_code ='F2O'"
            + " and  (s.product_code='DWLN' or s.product_code='AWLN')"
            + " and to_number(s.subscriber_no) between ds.FROM_NO and ds.TO_NO"
            + " and ds.logical_dvc_id = 'ATENIL'";

    protected static String RES = " select distinct(s.subscriber_no),s.SYS_CREATION_DATE, "
            +"s.sub_status_rsn_code, "
            + "nd.last_business_name  || ' ' ||rtrim(nd.first_name)as bajanord, "
            + "rtrim(ad.adr_place_type)|| ' ' || rtrim(ad.adr_city) || '  ' || "
            + "rtrim(ad.adr_primary_ln)|| ' ' ||rtrim(ad.adr_street_type)|| ' ' || "
            + "rtrim(ad.adr_house_no) || '  ' || rtrim(ad.adr_apt_type)|| ' ' ||ad.adr_apt_nm as hasce, "
            + "s.effective_date ,sf.additional_info , sf.soc ,ds.logical_dvc_id "
            + "from amnappo.subscriber s ,amnrefwork.logic_devs ds ,amnappo.service_feature sf , "
            + "amnappo.address_name_link anl, "
            + "amnappo.address_data      ad, "
            + "amnappo.name_data         nd "

            +" where  trunc(s.EFFECTIVE_DATE)>=trunc(sysdate-1) "
            + "and anl.ban=s.customer_id "
            +"and anl.expiration_date is null "
            + "and anl.link_type = 'T' "
            + "and ad.address_id=anl.address_id "
            + "and nd.name_id=anl.name_id "
            + "and s.subscriber_no=sf.subscriber_no "
            + "and s.customer_id=sf.ban "
            + "and sf.feature_code = 'FLPORT' "
            + "and sf.ftr_expiration_date  is NULL "
            + "and s.SUB_STATUS_LAST_ACT ='RSP' "
            + "and (sub_status_rsn_code ='COLL' or sub_status_rsn_code ='COL') "
            + "and  (s.product_code='DWLN' or s.product_code='AWLN') "
            + "and to_number(s.subscriber_no) between ds.FROM_NO and ds.TO_NO "
            + "and ds.logical_dvc_id = 'ATENIL'";
    protected static String error_LOGSelect = "select distinct(s.GROUP_TRX_SEQ_NO),s.TRX_STATUS, a.ERROR_USER_TEXT,s.SYS_CREATION_DATE "
                                              + "from TMPLOAD.CRMTRX_TRANSACTION_LOG s ,TMPLOAD.PROCESSTRX_ERROR_LOG a "
                                              + " where s.GROUP_TRX_SEQ_NO= a.GROUP_TRX_SEQ_NO and s.TRX_STATUS='E' and s.APPLICATION_ID = 'ACCP' and  s.SYS_CREATION_DATE like ? ";
    private static final Logger log = Logger.getLogger(HibernateUtil.class);




   public List<Subscriber> _proccessCan_Res_Sus (String select){
       Session session = null;
       Transaction transaction = null;
       List<Subscriber> Subscribers = new ArrayList<Subscriber>();
      try{
          log.info("Start collecting data from db Ararat...");
          session = getSessionFromcnf("hibernate-ararat.cfg.xml");
          transaction = session.beginTransaction();
          List<Object[]> tab1 =  session.createNativeQuery(returnSelectAction(select)).list();
          transaction.commit();
          for (Object[] tab : tab1) {
              //String employee= (String)tab1.get(0)[3];
              Subscriber sb = new Subscriber();
              sb.setSubscriber_no((String)tab[0]);
              sb.setSys_creation_date((Timestamp)tab[1]);
              sb.setSubscriber((String)tab[3]);
              sb.setAddress((String)tab[4]);
              sb.setEffective_date((Timestamp)tab[5]);
              sb.setAdditional_info((String)tab[6]);
              sb.setSoc((String)tab[7]);
              sb.setLogical_dvc_id((String)tab[8]);
              Subscribers.add(sb);
              // popo =data_creat;
          }
      }
      catch (Exception e) {
          log.error("Collecting process from db Ararat is crashed... ");
          e.printStackTrace();
      } finally {
          if (session != null) {
              session.close();
          }
          // session2.close();
      }
       return Subscribers;
   }
    public Session getSessionFromcnf(String config){
        Session retSession=null;
        SessionFactory sessionfactory =null;
        log.info("read config file from  " + config);
        ServiceRegistry srvcReg=null;
        try{
        Configuration configuration = new Configuration();
        configuration.configure(config);
        srvcReg = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionfactory = configuration.buildSessionFactory(srvcReg);
        retSession  = sessionfactory.openSession();
        }catch(Exception e){
            if (srvcReg!=null)
                StandardServiceRegistryBuilder.destroy(srvcReg);
            log.error("Exception while initializing hibernate util.. ");
            e.printStackTrace();
        }
        catch(Throwable t){
            System.err.println("Exception while getting session.. ");
            log.error("Exception while getting session.. ");
            t.printStackTrace();
        }
        return retSession;
    }
    private String returnSelectAction(String txt)
    {
        String res=null;
        switch (txt) {
            case "DEL": res = DEL;
            break;
            case "RES": res = RES;
            break;
            case "SUS": res = SUS;
            break;
        }
        return res;
    }
}


