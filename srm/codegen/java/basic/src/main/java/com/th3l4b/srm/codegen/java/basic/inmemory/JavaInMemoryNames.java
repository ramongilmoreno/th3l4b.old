package com.th3l4b.srm.codegen.java.basic.inmemory;

import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;

public class JavaInMemoryNames {

	private BaseNames _baseNames;

	public JavaInMemoryNames(BaseNames baseNames) {
		_baseNames = baseNames;
	}

	public String packageForInMemory(JavaCodeGeneratorContext context) {
		return context.getPackage() + ".inmemory";
	}

	public String fqnInMemory(String clazz, JavaCodeGeneratorContext context) {
		return packageForInMemory(context) + "." + clazz;
	}

	public String finderInMemory(INormalizedModel model) throws Exception {
		return "Abstract" + _baseNames.name(model) + "InMemoryFinder";
	}

	public String abstractInMemoryContext(INormalizedModel model)
			throws Exception {
		return "Abstract" + _baseNames.name(model) + "InMemorySRMContext";
	}

}
