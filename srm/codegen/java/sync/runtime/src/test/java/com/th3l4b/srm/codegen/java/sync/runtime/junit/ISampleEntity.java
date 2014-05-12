package com.th3l4b.srm.codegen.java.sync.runtime.junit;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface ISampleEntity extends IRuntimeEntity<ISampleEntity> {

	boolean isSetField1() throws Exception;
	
	String getField1() throws Exception;

	void setField1(String value) throws Exception;
	
	boolean isSetRelation() throws Exception;

	IIdentifier getRelation() throws Exception;

	void setRelation(IIdentifier relation) throws Exception;

}