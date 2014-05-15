package com.th3l4b.srm.codegen.java.basic.runtime.inmemory;

import java.util.Map;

import com.th3l4b.srm.codegen.java.basic.runtime.AbstractSRMContext;
import com.th3l4b.srm.codegen.java.basic.runtime.update.IUpdateToolFinder;
import com.th3l4b.srm.codegen.java.basic.runtime.update.IUpdateToolUpdater;
import com.th3l4b.srm.runtime.IFinder;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractInMemorySRMContext<FINDER extends IFinder> extends
		AbstractSRMContext<FINDER> {

	protected abstract Map<IIdentifier, IRuntimeEntity<?>> getEntities()
			throws Exception;

	@Override
	protected IUpdateToolFinder getUpdateToolFinder() throws Exception {
		return new AbstractInMemoryUpdaterAndFinder() {
			@Override
			protected Map<IIdentifier, IRuntimeEntity<?>> getEntities()
					throws Exception {
				return AbstractInMemorySRMContext.this.getEntities();
			}
		};
	}

	@Override
	protected IUpdateToolUpdater getUpdateToolUpdater() throws Exception {
		return new AbstractInMemoryUpdaterAndFinder() {
			@Override
			protected Map<IIdentifier, IRuntimeEntity<?>> getEntities()
					throws Exception {
				return AbstractInMemorySRMContext.this.getEntities();
			}
		};
	}

}
