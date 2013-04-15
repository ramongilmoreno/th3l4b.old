package com.th3l4b.srm.codegen.java.basicruntime.junit.basicruntime;

import com.th3l4b.srm.codegen.java.basicruntime.junit.IEntityA;
import com.th3l4b.srm.codegen.java.basicruntime.junit.ITestFinder;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractRuntimeEntity;
import com.th3l4b.srm.runtime.IIdentifier;

public class DefaultEntityA extends AbstractRuntimeEntity<IEntityA> implements
		IEntityA {

	protected IIdentifier _EntityA;

	@Override
	public Class<IEntityA> clazz() {
		return IEntityA.class;
	}

	@Override
	public IIdentifier getEntityA() throws Exception {
		return _EntityA;
	}

	@Override
	public IEntityA getEntityA(ITestFinder finder) throws Exception {
		return _EntityA != null ? finder.getEntityA(_EntityA) : null;
	}

	@Override
	public void setEntityA(IEntityA value) throws Exception {
		_EntityA = value != null ? value.coordinates().getIdentifier() : null;
	}

	@Override
	public void setEntityA(IIdentifier identifier) throws Exception {
		_EntityA = identifier;
	}

}
