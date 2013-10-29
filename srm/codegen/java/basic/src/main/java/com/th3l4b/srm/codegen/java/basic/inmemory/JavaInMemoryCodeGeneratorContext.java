package com.th3l4b.srm.codegen.java.basic.inmemory;

import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;

public class JavaInMemoryCodeGeneratorContext extends JavaCodeGeneratorContext {
	
	private JavaInMemoryNames _javaInMemoryNames = new JavaInMemoryNames();
	
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
