package com.rojaware.query.model;

import org.apache.commons.lang3.StringUtils;

public class StringHelper {
public static boolean isEmptyOrWhiteSpaceOrNull(Object o) {
		
		if (o == null)
			return true;
		else {
			String s = "";
			if ( o instanceof Integer) { 
				s = Integer.toString((Integer)o);
			} else {
				s = (String)o;
				if (s.equalsIgnoreCase("null"))
					s = "";
			}
		    return (StringUtils.isBlank(s)) ? true : false;
		}
	}
}
