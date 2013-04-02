package com.th3l4b.srm.codegen.java.basic;

import com.th3l4b.srm.codegen.base.CodeGeneratorContext;

public class JavaCodeGeneratorContext extends CodeGeneratorContext {

	private String _package;

	public String getPackage() {
		return _package;
	}

	public void setPackage(String pkg) {
		_package = pkg;
	}

	public void copyTo(JavaCodeGeneratorContext to) throws Exception {
		super.copyTo(to);
		to.setPackage(getPackage());
	}

}
