package com.rojaware.common.tester;

import org.apache.commons.lang3.math.NumberUtils;

public class App 
{
    public static void main( String[] args )
    {
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
