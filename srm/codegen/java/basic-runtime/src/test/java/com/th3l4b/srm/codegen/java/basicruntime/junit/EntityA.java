package com.th3l4b.srm.codegen.java.basicruntime.junit;

import com.th3l4b.srm.runtime.IIdentifier;

public interface EntityA extends ITest_ModelEntity<EntityA> {
	IIdentifier getEntityA () throws Exception;
	EntityA getEntityA (ITest_Finder finder) throws Exception; 
}
