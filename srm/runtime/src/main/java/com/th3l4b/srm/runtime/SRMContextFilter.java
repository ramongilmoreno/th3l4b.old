package com.th3l4b.srm.runtime;

import java.util.Map;

public class SRMContextFilter<FINDER extends IFinder> implements ISRMContext<FINDER> {

	private ISRMContext<FINDER> _delegated;
	
	public IModelUtils getUtils() throws Exception {
		return _delegated.getUtils();
	}

	public void setUtils(IModelUtils utils) throws Exception {
		_delegated.setUtils(utils);
	}

	public FINDER getFinder() throws Exception {
		return _delegated.getFinder();
	}

	public void setFinder(FINDER finder) throws Exception {
		_delegated.setFinder(finder);
	}

	public void update(Map<IIdentifier, IRuntimeEntity<?>> entities)
			throws Exception {
		_delegated.update(entities);
	}

	public SRMContextFilter(ISRMContext<FINDER> delegated) {
		_delegated = delegated;
	}
	
	public ISRMContext<FINDER> getDelegated() {
		return _delegated;
	}
	
	public void setDelegated(ISRMContext<FINDER> delegated) {
		_delegated = delegated;
	}
	
	
	
}
