package com.th3l4b.srm.codegen.java.sync;

import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;

public class SyncCodeGeneratorContext extends JavaCodeGeneratorContext {

	private SyncNames _syncNames;

	public SyncCodeGeneratorContext(BaseNames baseNames) {
		super(baseNames);
		_syncNames = new SyncNames(baseNames);
	}
	
	public SyncNames getSyncNames() {
		return _syncNames;
	}
	
	public void setSyncNames(SyncNames syncNames) {
		_syncNames = syncNames;
	}

	public void copyTo(SyncCodeGeneratorContext to) throws Exception {
		super.copyTo(to);
		to.setSyncNames(getSyncNames());
	}

}
