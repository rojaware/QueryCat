package com.rojaware.common.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rojaware.query.dao.QueryDAO;
import com.rojaware.query.model.Query;

public class JdbcTemplateApp 
{
	final static Logger LOG = Logger.getLogger(JdbcTemplateApp.class);
    public static void main( String[] args )
    {
    	
    	//if you have time, 
    	//it's better to create an unit test rather than testing like this :)
      
    	 ApplicationContext context = 
    		new ClassPathXmlApplicationContext("spring.xml");
    	 
    	 QueryDAO queryDAO = (QueryDAO) context.getBean("queryDAO");
    	 String testJson = "{name='mkyong', age=35, position='Founder', salary=10000}";
         Query query1 = new Query( "count account","SELELCT COUNT(*) FROM ACCOUNT", testJson);
         Query query3 = new Query( "count address","SELELCT COUNT(*) FROM ADDRESS", testJson);
         Query query2 = new Query( "count client","SELELCT COUNT(*) FROM CLIENT", testJson);
  
         List<Query>querys = new ArrayList<Query>();
         querys.add(query1);
         querys.add(query2);
         querys.add(query3);
  
         queryDAO.insertBatch(querys);

         String sql = "UPDATE QUERY SET NAME ='SELECT DB_NAME()'";
         queryDAO.insertBatchSQL(sql);
         
         LOG.info("Batch Insert Done!");
         
         Query queryA = queryDAO.findQueryById(1);
         LOG.info("Query A : " + queryA);
         
         Query queryB = queryDAO.findByQueryId2(1);
         LOG.info("Query B : " + queryB);
         
         List<Query> queryAs = queryDAO.list();
         for(Query query : queryAs){
         	 LOG.info("Query As : " + queryAs);
         }
        
         List<Query> queryBs = queryDAO.findAll2();
         for(Query query : queryBs){
         	 LOG.info("Query Bs : " + queryBs);
         }
         
         String queryName = queryDAO.findQueryNameById(1);
         LOG.info("Query Name : " + queryName);
         
         int total = queryDAO.findTotalQuery();
         LOG.info("Total : " + total);
         
         //delete all records
         queryDAO.insertBatchSQL("DELETE FROM QUERY");
         LOG.info("Records Deleted!");
         
       
    }
    public void initializeLogger()
    {
      Properties logProperties = new Properties();
   
      try
      {
        // load our log4j properties / configuration file
        logProperties.load(new FileInputStream("log4j.properties"));
        PropertyConfigurator.configure(logProperties);
        LOG.info("Logging initialized.");
      }
      catch(IOException e)
      {
        throw new RuntimeException("Unable to load logging property " );
      }
    }
}
