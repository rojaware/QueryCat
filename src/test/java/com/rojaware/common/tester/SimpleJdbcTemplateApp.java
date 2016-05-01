package com.rojaware.common.tester;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rojaware.query.dao.QueryDAO;
import com.rojaware.query.model.Query;

public class SimpleJdbcTemplateApp 
{
	private static final Logger LOG = Logger.getLogger(SimpleJdbcTemplateApp.class);

    public static void main( String[] args )
    {
    	//if you have time, 
    	//it's better to create an unit test rather than testing like this :)
    	
    	ApplicationContext context = 
    		new ClassPathXmlApplicationContext("Spring-Module.xml");
    	
    	QueryDAO querySimpleDAO = (QueryDAO) context.getBean("querySimpleDAO");
        Query query1 = new Query( "list account","SELELCT * FROM ACCOUNT", null);
        Query query3 = new Query( "list address","SELELCT * FROM ADDRESS", null);
        Query query2 = new Query( "list client","SELELCT * FROM CLIENT", null);
 
        List<Query>querys = new ArrayList<Query>();
        querys.add(query1);
        querys.add(query2);
        querys.add(query3);
 
        querySimpleDAO.insertBatch(querys);

        String sql = "UPDATE QUERY SET NAME ='SELECT DB_NAME() '";
        querySimpleDAO.insertBatchSQL(sql);
        
        LOG.info("Batch Insert Done!");
    	
        Query queryA = querySimpleDAO.findQueryById(1);
        LOG.info("Query A : " + queryA);
        
        Query queryB = querySimpleDAO.findByQueryId2(1);
        LOG.info("Query B : " + queryB);
        
        List<Query> queryAs = querySimpleDAO.list();
        for(Query cust: queryAs){
        	 LOG.debug("Query As : " + queryAs);
        }
       
        List<Query> queryBs = querySimpleDAO.findAll2();
        for(Query cust: queryBs){
        	 LOG.info("Query Bs : " + queryBs);
        }
        
        String queryName = querySimpleDAO.findQueryNameById(1);
        LOG.info("Query Name : " + queryName);
        
        int total = querySimpleDAO.findTotalQuery();
        LOG.info("Total : " + total);
        
        
        Query query4 = new Query( "read account","SELELCT top 1 * FROM ACCOUNT", null);
        Query query5 = new Query( "read address","SELELCT top 1 * FROM ADDRESS", null);
        Query query6 = new Query( "read client","SELELCT top 1 * FROM CLIENT", null);
 
        List<Query>querys2 = new ArrayList<Query>();
        querys2.add(query4);
        querys2.add(query5);
        querys2.add(query6);
        
        querySimpleDAO.insertBatchNamedParameter2(querys2);
        
        //delete all records
        querySimpleDAO.insertBatchSQL("DELETE FROM QUERY");
        LOG.info("Records Deleted!");
    }
}
