package com.th3l4b.srm.codegen.java.sync.runtime.junit;

import com.th3l4b.srm.codegen.java.sync.runtime.AbstractSRMContextUpdateFilter;

public abstract class AbstractSampleModelUpdateFilter extends
		AbstractSRMContextUpdateFilter<ISampleFinder> implements
		ISampleModelContext {

	public AbstractSampleModelUpdateFilter(ISampleModelContext delegated) {
		super(delegated);
	}
}
