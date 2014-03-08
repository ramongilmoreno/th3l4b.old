package com.th3l4b.srm.codegen.java.basic;

import com.th3l4b.srm.codegen.base.CodeGeneratorContext;
import com.th3l4b.srm.codegen.base.names.BaseNames;

public class JavaCodeGeneratorContext extends CodeGeneratorContext {

	private String _package;

	private JavaNames _javaNames;

	public JavaCodeGeneratorContext(BaseNames baseNames) {
		super(baseNames);
		_javaNames = new JavaNames(baseNames);
	}

	public String getPackage() {
		return _package;
	}

	public void setPackage(String pkg) {
		_package = pkg;
	}

	public JavaNames getJavaNames() {
		return _javaNames;
	}

	public void setJavaNames(JavaNames javaNames) {
		_javaNames = javaNames;
	}

	public void copyTo(JavaCodeGeneratorContext to) throws Exception {
		super.copyTo(to);
		to.setJavaNames(getJavaNames());
		to.setPackage(getPackage());
	}

}
