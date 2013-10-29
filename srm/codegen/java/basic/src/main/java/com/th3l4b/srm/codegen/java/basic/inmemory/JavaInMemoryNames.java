package com.th3l4b.srm.codegen.java.basic.inmemory;

import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.basic.JavaNames;

public class JavaInMemoryNames extends JavaNames {
	
	public String packageForInMemory(JavaCodeGeneratorContext context) {
		return context.getPackage() + ".inmemory";
	}

	public String fqnInMemory(String clazz, JavaCodeGeneratorContext context) {
		return packageForInMemory(context) + "." + clazz;
	}


	public String finderInMemory(INormalizedModel model) throws Exception {
		return "Abstract"
				+ valueOrProperty(javaIdentifier(model.getName())
						+ "InMemoryFinder", model);
	}

	public String abstractInMemoryContext(INormalizedModel model) throws Exception {
		return "Abstract"
				+ valueOrProperty(javaIdentifier(model.getName()) + "InMemorySRMContext",
						model);
	}

}
