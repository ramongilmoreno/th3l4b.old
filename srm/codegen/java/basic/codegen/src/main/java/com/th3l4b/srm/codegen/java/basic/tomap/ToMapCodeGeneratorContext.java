package com.th3l4b.srm.codegen.java.basic.tomap;

import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;

public class ToMapCodeGeneratorContext extends JavaCodeGeneratorContext {

	private ToMapNames _toMapNames;

	public ToMapCodeGeneratorContext(BaseNames baseNames) {
		super(baseNames);
		_toMapNames = new ToMapNames(baseNames);
	}

	public ToMapNames getToMapNames() {
		return _toMapNames;
	}

	public void setToMapNames(ToMapNames toMapNames) {
		_toMapNames = toMapNames;
	}

	public void copyTo(ToMapCodeGeneratorContext to) throws Exception {
		super.copyTo(to);
		to.setToMapNames(getToMapNames());
	}

}
