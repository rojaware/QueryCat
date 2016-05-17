/**
 * 
 */
package com.rojaware.query.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
 
public class MyRoutingDataSource extends AbstractRoutingDataSource {
	final static Logger LOG = Logger.getLogger(MyRoutingDataSource.class);
	String env;
	
//    @Override
//    protected Object determineCurrentLookupKey() {
//    	
//        String catalog = DBContextHolder.getCatelog();
//        if (catalog == null) {
//        	LOG.error("target database is null");
//        }
//		
//        LOG.info("New DB environment obtained: "+ catalog);
//        return catalog;
//    }
    @Override
    protected Object determineCurrentLookupKey() {
        return env;
    }

	public void setEnv(String env) {
		this.env = env;
	}

	public String getEnv() {
		return env;
	}
    
}