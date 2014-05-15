package com.th3l4b.srm.codegen.java.web.rest.codegen.junit;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface ISampleEntityA extends IRuntimeEntity<ISampleEntityA> {

	String getField1() throws Exception;

	void setField1(String value) throws Exception;

	IIdentifier getRelation() throws Exception;

	void setRelation(IIdentifier relation) throws Exception;

}
