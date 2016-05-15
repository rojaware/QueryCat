/**
 * 
 */
package com.rojaware.query.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
 
public class MyRoutingDataSource extends AbstractRoutingDataSource {
	
	
	final static Logger LOG = Logger.getLogger(MyRoutingDataSource.class);
	
    @Override
    protected Object determineCurrentLookupKey() {
    	
        String catalog = DBContextHolder.getCateloge();
        if (catalog == null) {
        	LOG.error("target database is null");
        }
		
        LOG.info("New DB environment obtained: "+ catalog);
        return catalog;
    }
    
}