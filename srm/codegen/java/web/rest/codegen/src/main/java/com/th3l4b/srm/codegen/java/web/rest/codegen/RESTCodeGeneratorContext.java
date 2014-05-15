package com.th3l4b.srm.codegen.java.web.rest.codegen;

import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;

public class RESTCodeGeneratorContext extends JavaCodeGeneratorContext {

	private RESTNames _RESTNames;

	public RESTCodeGeneratorContext(BaseNames baseNames) {
		super(baseNames);
		_RESTNames = new RESTNames(baseNames);
	}

	public RESTNames getRESTNames() {
		return _RESTNames;
	}

	public void setRESTNames(RESTNames toMapNames) {
		_RESTNames = toMapNames;
	}

	public void copyTo(RESTCodeGeneratorContext to) throws Exception {
		super.copyTo(to);
		to.setRESTNames(getRESTNames());
	}

}
