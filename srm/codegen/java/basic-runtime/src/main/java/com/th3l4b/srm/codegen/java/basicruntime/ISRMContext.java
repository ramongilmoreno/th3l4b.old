package com.th3l4b.srm.codegen.java.basicruntime;

import java.util.Map;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface ISRMContext<FINDER> {
	IModelUtils getUtils () throws Exception;
	void setUtils (IModelUtils utils) throws Exception;
	FINDER getFinder () throws Exception;
	void setFinder (FINDER finder) throws Exception;
	void update (Map<IIdentifier, IRuntimeEntity<?>> entities) throws Exception;
}
