package com.th3l4b.srm.codegen.java.sync.runtime.junit;

import com.th3l4b.srm.runtime.IFinder;
import com.th3l4b.srm.runtime.IIdentifier;

public interface ISampleFinder extends IFinder {
	ISampleEntity getSampleEntity (IIdentifier id) throws Exception;
	Iterable<ISampleEntity> allSampleEntity () throws Exception;
	Iterable<ISampleEntity> findAllSampleEntityFromOther (IIdentifier other) throws Exception;
	
}
