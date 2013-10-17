package com.th3l4b.srm.codegen.java.jdbcruntime;

import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface ISampleEntity extends IRuntimeEntity<ISampleEntity> {

	String getField1 () throws Exception;
	void setField1 (String value) throws Exception;
	
}
