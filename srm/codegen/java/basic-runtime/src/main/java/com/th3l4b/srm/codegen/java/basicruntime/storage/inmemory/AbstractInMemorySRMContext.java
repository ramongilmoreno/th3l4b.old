package com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory;

import java.util.Map;

import com.th3l4b.srm.codegen.java.basicruntime.AbstractSRMContext;
import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolFinder;
import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolUpdater;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractInMemorySRMContext<FINDER> extends
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
