package com.th3l4b.testbed.integrated.model;

import java.util.HashSet;
import java.util.Map;

import com.th3l4b.srm.codegen.java.sync.runtime.AbstractSRMContextUpdateFilter;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.testbed.integrated.model.generated.ISyncEntity;
import com.th3l4b.testbed.integrated.model.generated.base.INameForIntegratedTestContext;
import com.th3l4b.testbed.integrated.model.generated.base.INameForIntegratedTestFinder;

public class SyncTest {

	public void test(INameForIntegratedTestContext left,
			INameForIntegratedTestContext right) throws Exception {
		final HashSet<IIdentifier> updatesLeft = new HashSet<IIdentifier>();
		AbstractSRMContextUpdateFilter<INameForIntegratedTestFinder> lcontext = new AbstractSRMContextUpdateFilter<INameForIntegratedTestFinder>(
				left) {
			@Override
			protected void recordUpdate(
					Map<IIdentifier, IRuntimeEntity<?>> entities)
					throws Exception {
				for (IIdentifier id : entities.keySet()) {
					updatesLeft.add(id);
				}
			}
		};

		
		final HashSet<IIdentifier> updatesRight = new HashSet<IIdentifier>();
		AbstractSRMContextUpdateFilter<INameForIntegratedTestFinder> rcontext = new AbstractSRMContextUpdateFilter<INameForIntegratedTestFinder>(
				right) {
			@Override
			protected void recordUpdate(
					Map<IIdentifier, IRuntimeEntity<?>> entities)
					throws Exception {
				for (IIdentifier id : entities.keySet()) {
					updatesRight.add(id);
				}
			}
		};
		
	}

}
