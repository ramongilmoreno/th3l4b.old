package com.th3l4b.srm.runtime;

public class DatabaseUtils {
	public static String column(String name, boolean forField) throws Exception {
		// SQL names start with letter
		// http://db.apache.org/derby/docs/10.1/ref/crefsqlj1003454.htm
		return (forField ? "f_" : "s_") + name;
	}

}
