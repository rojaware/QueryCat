package com.rojaware.common.tester;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.rojaware.query.config.ConfigManager;
import com.rojaware.query.config.Configuration;

public class YmlWriteTest 
{
    public static void main( String[] args )
    {
    	Configuration config = ConfigManager.instance().getConfig();
    	System.out.println("s : "+ config.getActive());
//    	System.out.println("list : "+ StringUtils.split(config.getDatabases(), ","));
    	YmlWriteTest.update("company");
    	
    	
    }
    public static void update(String s)
    {
    	Configuration config = ConfigManager.instance().getConfig();
    	config.setActive(s);
    	
    	ConfigManager.instance().save();
    	System.out.println("s : "+ config.getActive());
    }
}
