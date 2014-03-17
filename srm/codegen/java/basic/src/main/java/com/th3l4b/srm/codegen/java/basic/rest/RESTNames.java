package com.th3l4b.srm.codegen.java.basic.rest;

import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.names.BaseNames;

public class RESTNames {

	private BaseNames _baseNames;

	public RESTNames(BaseNames baseNames) {
		_baseNames = baseNames;
	}

	public String packageForREST(RESTCodeGeneratorContext context) {
		return context.getPackage() + ".rest";
	}

	public String fqnREST(String clazz, RESTCodeGeneratorContext context) {
		return packageForREST(context) + "." + clazz;
	}

	public String parserREST(INormalizedEntity entity) throws Exception {
		return _baseNames.identifier(entity.getName()) + "Parser";
	}

	public String finder(INormalizedModel model) throws Exception {
		return _baseNames.identifier(model.getName())
				+ "RESTFinder";
	}

}
