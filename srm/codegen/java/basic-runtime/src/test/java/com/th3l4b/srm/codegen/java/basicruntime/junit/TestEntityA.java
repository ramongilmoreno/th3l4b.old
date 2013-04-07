package com.th3l4b.srm.codegen.java.basicruntime.junit;

import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractRuntimeEntity;

public class TestEntityA extends AbstractRuntimeEntity<ITestEntityA> implements ITestEntityA {

	@Override
	public Class<ITestEntityA> clazz() {
		return ITestEntityA.class;
	}
}
