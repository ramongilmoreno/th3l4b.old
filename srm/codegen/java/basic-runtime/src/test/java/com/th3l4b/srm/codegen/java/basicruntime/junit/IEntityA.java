package com.th3l4b.srm.codegen.java.basicruntime.junit;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface IEntityA extends IRuntimeEntity<IEntityA> {
	IIdentifier getEntityA () throws Exception;
	IEntityA getEntityA (ITestFinder finder) throws Exception;
	void setEntityA (IIdentifier identifier) throws Exception;
	void setEntityA(IEntityA value) throws Exception;
}
