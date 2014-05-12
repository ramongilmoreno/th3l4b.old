package com.th3l4b.srm.codegen.java.sync.runtime.junit;

import com.th3l4b.srm.codegen.java.sync.runtime.DefaultDiffContext;

public class SampleModelDiffContext extends DefaultDiffContext {

	public SampleModelDiffContext() throws Exception {
		put(ISampleEntity.class, new SampleEntityDiff());
	}
}
