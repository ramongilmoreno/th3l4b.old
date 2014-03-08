package com.th3l4b.android.srm.codegen;

public class AndroidNames {

	public String packageForAndroid (AndroidCodeGeneratorContext context) {
		return context.getPackage() + ".android";
	}
}
