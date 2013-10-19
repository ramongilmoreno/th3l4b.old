package com.th3l4b.srm.codegen.java.basic.storage.inmemory;

import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.java.basic.JavaNames;

public class JavaInMemoryNames extends JavaNames {

	public String finderInMemory(INormalizedModel model) throws Exception {
		return "Abstract"
				+ valueOrProperty(javaIdentifier(model.getName())
						+ "InMemoryFinder", model);
	}

	public String abstractInMemoryContext(INormalizedModel model) throws Exception {
		return "Abstract"
				+ valueOrProperty(javaIdentifier(model.getName()) + "Context",
						model);
	}

}
