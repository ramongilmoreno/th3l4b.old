package com.th3l4b.srm.codegen.java.mongoruntime.junit;

import com.th3l4b.srm.codegen.java.mongoruntime.AbstractMongoFinder;
import com.th3l4b.srm.runtime.IIdentifier;

public abstract class AbstractSampleMongoFinder extends AbstractMongoFinder
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
