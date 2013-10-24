package com.th3l4b.srm.codegen.java.jdbcruntime;

import com.th3l4b.srm.runtime.IIdentifier;

public interface ISampleFinder {
	ISampleEntity getEntity (IIdentifier id) throws Exception;
	Iterable<ISampleEntity> allEntity() throws Exception;
	Iterable<ISampleEntity> findAllNeedFromIItem(ISampleEntity from) throws Exception;
}
