package com.th3l4b.srm.codegen.java.sync.runtime.junit;

import com.th3l4b.srm.codegen.java.sync.runtime.AbstractEntityDiff;

public class SampleEntityDiff extends AbstractEntityDiff<ISampleEntity> {

	@Override
	protected boolean diffRest(ISampleEntity from, ISampleEntity to,
			ISampleEntity diff) throws Exception {
		boolean r = false;
		
		if (to.isSetField1()) {
			if (!nullSafeEquals(from.getField1(), to.getField1())) {
				diff.setField1(to.getField1());
			}
		}

		return r;
	}

}
