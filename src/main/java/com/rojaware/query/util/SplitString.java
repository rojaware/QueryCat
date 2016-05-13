package com.rojaware.query.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplitString {

	/**
     * This program shows how to split a string in java
     * @param args
     */
 
    public static Map<String, Object> toParameterMap(String str) {
    	Pattern logEntry = Pattern.compile("\\{(.*?)\\}");
        Matcher matchPattern = logEntry.matcher(str);
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        
        while(matchPattern.find()) {
        	parameterMap.put(matchPattern.group(1), "");
        }
        return parameterMap;
    }
   
    

}
