package com.bee.am;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static String query1 = "select * from table1";
    private static String query2 = "select * from table2";
    private static String query3 = "select * from table3";
    private static String INSERT_SQL = "INSERT INTO VC_SMSC_PARTNER_TRUNK (TRUNK, MSC, PORT_TYPE) VALUES('Simo','333', '46')";
    private static final Logger log = Logger.getLogger(HibernateUtil.class);


    public Session getSessionFromcnf(String config){
        Session retSession=null;
        SessionFactory sessionfactory =null;
        log.info("read config file from  " + config);
        try{
        Configuration configuration = new Configuration();
        configuration.configure(config);
        ServiceRegistry srvcReg = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionfactory = configuration.buildSessionFactory(srvcReg);
        retSession  = sessionfactory.openSession();
        }catch(Exception e){
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



}
