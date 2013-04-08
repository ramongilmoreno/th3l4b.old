package com.th3l4b.srm.codegen.java.basicruntime.junit;

import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractRuntimeEntity;
import com.th3l4b.srm.runtime.IIdentifier;

public class Default_EntityA extends AbstractRuntimeEntity<EntityA> implements
		EntityA {

	protected IIdentifier _EntityA;

	@Override
	public Class<EntityA> clazz() {
		return EntityA.class;
	}

	@Override
	public IIdentifier getEntityA() throws Exception {
		return _EntityA;
	}

	@Override
	public EntityA getEntityA(ITest_Finder finder) throws Exception {
		return _EntityA != null ? finder.getEntityA(_EntityA) : null;
	}

}
