package com.th3l4b.srm.codegen.java.basicruntime.junit.basicruntime;

import com.th3l4b.srm.codegen.java.basicruntime.inmemory.AbstractRuntimeEntity;
import com.th3l4b.srm.codegen.java.basicruntime.junit.IEntityA;
import com.th3l4b.srm.codegen.java.basicruntime.junit.IEntityB;
import com.th3l4b.srm.codegen.java.basicruntime.junit.ITestFinder;
import com.th3l4b.srm.runtime.IIdentifier;

public class DefaultEntityA extends AbstractRuntimeEntity<IEntityA> implements
		IEntityA {

	protected boolean _isSet_EntityB;
	protected IIdentifier _EntityB;

	protected boolean _isSet_StringAttribute;
	protected String _stringAttribute;

	
	@Override
	public Class<IEntityA> clazz() {
		return IEntityA.class;
	}
	
	@Override
	public boolean isSetEntityB() throws Exception {
		return _isSet_EntityB;
	}

	@Override
	public IIdentifier getEntityB() throws Exception {
		return _EntityB;
	}

	@Override
	public IEntityB getEntityB(ITestFinder finder) throws Exception {
		return _EntityB == null ? null : finder.getEntityB(_EntityB);
	}

	@Override
	public void setEntityB(IIdentifier identifier) throws Exception {
		_isSet_EntityB = true;
		_EntityB = identifier;

	}

	@Override
	public void setEntityB(IEntityB value) throws Exception {
		IIdentifier id =  value == null ? null : value.coordinates().getIdentifier();
		setEntityB(id);
	}

	@Override
	public String getStringAttribute() throws Exception {
		return _stringAttribute;
	}

	@Override
	public boolean isSetStringAttribute() throws Exception {
		return _isSet_StringAttribute;
	}

	@Override
	public void setStringAttribute(String value) throws Exception {
		_isSet_StringAttribute = true;
		_stringAttribute = value;
	}
	
	

}
