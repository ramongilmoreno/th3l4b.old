package com.th3l4b.srm.runtime;

import java.util.Map;

public interface ISRMContext<FINDER> {
	IModelUtils getUtils () throws Exception;
	void setUtils (IModelUtils utils) throws Exception;
	FINDER getFinder () throws Exception;
	void setFinder (FINDER finder) throws Exception;
	void update (Map<IIdentifier, IRuntimeEntity<?>> entities) throws Exception;
}
