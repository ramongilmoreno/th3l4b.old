package com.th3l4b.srm.codegen.java.jdbcruntime;

import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractRuntimeEntity;

public class DefaultSampleEntity extends AbstractRuntimeEntity<ISampleEntity>
		implements ISampleEntity {

	private String _field;

	@Override
	public Class<ISampleEntity> clazz() {
		return ISampleEntity.class;
	}

	@Override
	public String getField1() throws Exception {
		return _field;
	}

	@Override
	public void setField1(String value) throws Exception {
		_field = value;
	}

}
