package com.th3l4b.srm.codegen.java.android;

import com.th3l4b.srm.codegen.java.basic.JavaNames;

public class AndroidNames extends JavaNames {

	public String packageForAndroid (AndroidCodeGeneratorContext context) {
		return context.getPackage() + ".android";
	}
}
