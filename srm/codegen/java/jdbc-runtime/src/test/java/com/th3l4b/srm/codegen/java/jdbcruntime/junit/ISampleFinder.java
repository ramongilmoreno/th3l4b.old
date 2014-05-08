package com.th3l4b.srm.codegen.java.jdbcruntime.junit;

import com.th3l4b.srm.runtime.IFinder;
import com.th3l4b.srm.runtime.IIdentifier;

public interface ISampleFinder extends IFinder {
	ISampleEntity getEntity (IIdentifier id) throws Exception;
	Iterable<ISampleEntity> allEntity() throws Exception;
	Iterable<ISampleEntity> findAllNeedFromIItem(ISampleEntity from) throws Exception;
}
