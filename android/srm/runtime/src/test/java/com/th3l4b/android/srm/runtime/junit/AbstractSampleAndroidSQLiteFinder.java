package com.th3l4b.android.srm.runtime.junit;

import com.th3l4b.android.srm.runtime.sqlite.AbstractAndroidSQLiteFinder;
import com.th3l4b.srm.codegen.java.basicruntime.inmemory.Pair;
import com.th3l4b.srm.runtime.IIdentifier;

public abstract class AbstractSampleAndroidSQLiteFinder extends
		AbstractAndroidSQLiteFinder implements ISampleFinder {

	public AbstractSampleAndroidSQLiteFinder() {
		_map.put(new Pair(ISampleOtherEntity.class, "Sample Entity"),
				"SampleEntity");
	}

	@Override
	public ISampleEntity getSampleEntity(IIdentifier id) throws Exception {
		return super.find(ISampleEntity.class, id);
	}
	
	@Override
	public Iterable<ISampleEntity> allSampleEntity() throws Exception {
		return all(ISampleEntity.class);
	}

	@Override
	public Iterable<ISampleEntity> findAllSampleEntityFromOther(
			IIdentifier other) throws Exception {
		return find(ISampleEntity.class, ISampleOtherEntity.class, other,
				"Sample Entity");
	}
}
