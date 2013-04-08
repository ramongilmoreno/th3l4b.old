package com.th3l4b.srm.codegen.java.basicruntime.junit;

import com.th3l4b.srm.runtime.IFinder;
import com.th3l4b.srm.runtime.IIdentifier;

public interface ITest_Finder extends IFinder {
	EntityA getEntityA (IIdentifier identifier) throws Exception;
	Iterable<EntityA> findEntityA (IIdentifier from) throws Exception;
}
