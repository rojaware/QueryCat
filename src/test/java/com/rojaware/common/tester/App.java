package com.rojaware.common.tester;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.rojaware.query.config.ConfigManager;
import com.rojaware.query.config.Configuration;

public class App 
{
    public static void main( String[] args )
    {
    	Configuration config = ConfigManager.instance().getConfig();
    	System.out.println("s : "+ config.getActive());
//    	System.out.println("list : "+ StringUtils.split(config.getDatabases(), ","));
    	if (App.isDouble("100.00"))
    		System.out.println("double");
    	else
    		System.out.println("integer");
    	
    }
    public static boolean isDouble(String s)
    {
		if (NumberUtils.isParsable(s) && s.contains(".")) {
			double d = Double.parseDouble(s);
			return d != (int) d;
		}
		return false;
    }
}
