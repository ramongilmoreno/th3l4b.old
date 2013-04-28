package com.th3l4b.srm.codegen.java.basicruntime.junit;

import com.th3l4b.srm.runtime.IFinder;
import com.th3l4b.srm.runtime.IIdentifier;

public interface ITestFinder extends IFinder {
	IEntityA getEntityA(IIdentifier identifier) throws Exception;

	IEntityB getEntityB(IIdentifier identifier) throws Exception;

	Iterable<IEntityA> allEntityA() throws Exception;

	Iterable<IEntityA> findEntityA(IIdentifier from) throws Exception;

	Iterable<IEntityA> findEntityAFromB(IIdentifier from) throws Exception;
}
