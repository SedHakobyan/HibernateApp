package com.bee.am;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

public class App {

    public void whenNamedQuery_thenMultipleEntityResult() {
     EntityManager em;


    }
    public static void main (String [] as)
    {
        Session session = null;
        Session session2 =null;
        Transaction transaction = null;

      try {
          HibernateUtil hib = new HibernateUtil();
         session = hib.getSessionFromcnf("hibernate-ararat.cfg.xml");
          //Configuration configuration = new Configuration();
         // configuration.configure("hibernate-crm.cfg.xml");
         // configuration.addAnnotatedClass(Employee.class);
        //  ServiceRegistry srvcReg = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        //  SessionFactory sessionFactory = configuration.buildSessionFactory(srvcReg);
         // session = sessionFactory.openSession();
         transaction = session.beginTransaction();

          //Native query selecting all columns
          //List<Object[]> tab1 =  session.createNativeQuery("select a.MSC, a.TRUNK, b.MSCID, a.PORT_TYPE,b.TYPE from VC_SMSC_PARTNER_TRUNK a,  VC_TRUNKS b where a.TRUNK = b.TRUNK").list();
    String test = "'"+"aaaa"+"||";
          System.out.println("test string = " + test);

          String select = " select distinct(s.subscriber_no),s.SYS_CREATION_DATE,s.sub_status_rsn_code ,"
                  + " nd.last_business_name  || ' ' ||rtrim(nd.first_name)as bajanord,"
                  + " rtrim(ad.adr_place_type)|| ' ' || rtrim(ad.adr_city) || '  ' ||"
                  + " rtrim(ad.adr_primary_ln)|| ' ' ||rtrim(ad.adr_street_type)|| ' ' ||"
                  + " rtrim(ad.adr_house_no) || '  ' || rtrim(ad.adr_apt_type)|| ' ' ||ad.adr_apt_nm as hasce,"
                  + " s.effective_date ,sf.additional_info , sf.soc ,ds.logical_dvc_id"
                  +  "    from amnappo.subscriber s ,amnrefwork.logic_devs ds ,amnappo.service_feature sf ,"
                  +   "      amnappo.address_name_link anl,"
                  +    "     amnappo.address_data      ad,"
                  +     "     amnappo.name_data         nd"

                  + " where  trunc(s.EFFECTIVE_DATE)=trunc(sysdate-90)"
                  + " and anl.ban=s.customer_id"
                  + " and anl.expiration_date is null"
                  + " and anl.link_type = 'T'"
                  + " and ad.address_id=anl.address_id"
                  + " and nd.name_id=anl.name_id"
                  + " and s.customer_id=sf.ban"
                  + " and s.subscriber_no=sf.subscriber_no"
                  + " and s.sub_status_rsn_code ='CO'"
                  + " and  trunc(sf.ftr_expiration_date)=trunc(sysdate-90)"
                  + " and sf.feature_code = 'FLPORT'"
                  + " and s.SUB_STATUS_LAST_ACT ='CAN'"
                  + " and  (s.product_code='DWLN' or s.product_code='AWLN')"
                  + " and to_number(s.subscriber_no) between ds.FROM_NO and ds.TO_NO"
                  + " and ds.dvc_tp='ate'";
          Timestamp popo = null;

         // List<Object[]> tab1 =  session.createNativeQuery(select).list();
         // System.out.println("erkarutyuyn = "+ tab1.size());
       //   for (Object[] tab : tab1) {
              //String employee= (String)tab1.get(0)[3];
           //   Timestamp data_creat = (Timestamp)tab1.get(0)[5];
           //   popo =data_creat;
            //  Trunk emp = (Trunk) results.get(1)[0];
          //    System.out.println("subscriber["+ data_creat +"]"+"\n");

          //}

          /*String INSERT_SQL = "INSERT INTO message_log (id, message, log_dttm) VALUES(id_seq.nextval, ?, SYSDATE)";
          em.createNativeQuery(INSERT_SQL).setParameter(1, message).executeUpdate();
          */
          String tme = "2019-10-01";
          String INSERT_SQL = "INSERT INTO VC_TRUNKS_DOUBLE (TRUNK, MSISDN, DOUBLE,COLUMN1) VALUES('66666','Gexam',1,to_date('"+ tme +"','YYYY-MM-DD'))";
          //session.createNativeQuery(INSERT_SQL).executeUpdate();

          transaction.commit();

         session2 = hib.getSessionFromcnf("hibernate-crm.cfg.xml");
          transaction = session2.beginTransaction();
          session2.createNativeQuery(INSERT_SQL).executeUpdate();

          transaction.commit();


      }
      catch (Exception e) {
          e.printStackTrace();
      } finally {
          if (session != null) {
              session.close();
          }
         // session2.close();
      }
    }

}
