package com.th3l4b.srm.codegen.java.web.rest.codegen.junit;

import com.th3l4b.srm.runtime.IFinder;
import com.th3l4b.srm.runtime.IIdentifier;

public interface ISampleFinder extends IFinder {
	ISampleEntityA getEntity (IIdentifier id) throws Exception;
	Iterable<ISampleEntityA> allEntity() throws Exception;
	Iterable<ISampleEntityB> findAllSampleEntityBFromEntityA(ISampleEntityA from) throws Exception;
	Iterable<ISampleEntityB> findAllSampleEntityBFromEntityA(IIdentifier from) throws Exception;
}
