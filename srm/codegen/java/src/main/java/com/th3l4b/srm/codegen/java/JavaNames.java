package com.th3l4b.srm.codegen.java;

import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.codegen.base.names.CamelUtils;

public class JavaNames {

	/**
	 * Creates a Java identifier out of the given input name.
	 */
	public static String javaIdentifier(String name) {
		return CamelUtils.toCamelCase(TextUtils.ASCII(name));
	}
}
