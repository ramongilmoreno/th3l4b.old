package com.th3l4b.srm.codegen.java.sync.runtime;

import java.util.Map;

import com.th3l4b.srm.runtime.IFinder;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.srm.runtime.ISRMContext;
import com.th3l4b.srm.runtime.SRMContextFilter;

/**
 * This context is capable of tracking the modifications sent to the
 * {@link #update(Map)} method and redirect them to other target with the
 * {@link #recordUpdate(Map)} abstract method.
 */
public abstract class AbstractSRMContextUpdateFilter<FINDER extends IFinder> extends
		SRMContextFilter<FINDER> {

	public AbstractSRMContextUpdateFilter(ISRMContext<FINDER> delegated) {
		super(delegated);
	}

	@Override
	public void update(Map<IIdentifier, IRuntimeEntity<?>> entities)
			throws Exception {
		super.update(entities);

		// Only after updates have been correctly processed it is possible to
		// record them
		recordUpdate(entities);
	}

	protected abstract void recordUpdate(
			Map<IIdentifier, IRuntimeEntity<?>> entities) throws Exception;

}
