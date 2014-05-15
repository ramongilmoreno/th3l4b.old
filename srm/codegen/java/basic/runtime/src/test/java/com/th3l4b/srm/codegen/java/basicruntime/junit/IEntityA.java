package com.th3l4b.srm.codegen.java.basicruntime.junit;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface IEntityA extends IRuntimeEntity<IEntityA> {
	boolean isSetEntityB () throws Exception;
	IIdentifier getEntityB () throws Exception;
	IEntityB getEntityB (ITestFinder finder) throws Exception;
	void setEntityB (IIdentifier identifier) throws Exception;
	void setEntityB(IEntityB value) throws Exception;
	String getStringAttribute () throws Exception;
	boolean isSetStringAttribute () throws Exception;
	void setStringAttribute (String value) throws Exception;
}
