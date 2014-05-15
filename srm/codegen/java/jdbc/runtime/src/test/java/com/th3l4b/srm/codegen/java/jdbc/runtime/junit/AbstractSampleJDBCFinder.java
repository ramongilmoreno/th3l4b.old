package com.th3l4b.srm.codegen.java.jdbc.runtime.junit;

import com.th3l4b.srm.codegen.java.jdbc.runtime.AbstractJDBCFinder;
import com.th3l4b.srm.runtime.IIdentifier;

public abstract class AbstractSampleJDBCFinder extends AbstractJDBCFinder
		implements ISampleFinder {
	public ISampleEntity getEntity(IIdentifier id) throws Exception {
		return find(ISampleEntity.class, id);
	}

	@Override
	public Iterable<ISampleEntity> allEntity() throws Exception {
		return all(ISampleEntity.class);
	}

	@Override
	public Iterable<ISampleEntity> findAllNeedFromIItem(ISampleEntity from)
			throws Exception {
		return find(ISampleEntity.class, from.getRelation(), "Column");
	}
}
