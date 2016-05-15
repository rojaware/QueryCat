package com.rojaware.query.dao.impl;

import org.apache.log4j.Logger;

import com.rojaware.query.config.ConfigManager;
import com.rojaware.query.config.Configuration;

public class DBContextHolder {

    
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    private static Logger LOG = Logger.getLogger(DBContextHolder.class);
    public static void clearCatelog() {
        contextHolder.remove();
    }

    public static String getCateloge() {
    	LOG.debug(" get catelog : {}"+ DBContextHolder.contextHolder.get());
    	Configuration config = ConfigManager.instance().getConfig();
    	String catalog = config.getActive();
    	System.out.println("reading active db from config : "+ catalog);
    	contextHolder.set(catalog);
        return contextHolder.get();
    }

    public static void setCatelog(String catelog) {
    	LOG.debug(" set catelog : {}"+ catelog);
    	Configuration config = ConfigManager.instance().getConfig();
    	config.setActive(catelog);
    	contextHolder.set(catelog);
    	ConfigManager.instance().save();
    }
}
