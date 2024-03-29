package com.th3l4b.srm.codegen.java.basic.inmemory;

import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;

public class JavaInMemoryCodeGeneratorContext extends JavaCodeGeneratorContext {

	private JavaInMemoryNames _javaInMemoryNames;

	public JavaInMemoryCodeGeneratorContext(BaseNames baseNames) {
		super(baseNames);
		_javaInMemoryNames = new JavaInMemoryNames(baseNames);
	}

	public JavaInMemoryNames getJavaInMemoryNames() {
		return _javaInMemoryNames;
	}

	public void setJavaInMemoryNames(JavaInMemoryNames javaInMemoryNames) {
		_javaInMemoryNames = javaInMemoryNames;
	}

	public void copyTo(JavaInMemoryCodeGeneratorContext to) throws Exception {
		super.copyTo(to);
		to.setJavaInMemoryNames(getJavaInMemoryNames());
	}

}
