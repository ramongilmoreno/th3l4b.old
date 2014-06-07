package com.th3l4b.srm.codegen.java.basic.runtime.inmemory;

import java.util.HashMap;
import java.util.Map;

import com.th3l4b.srm.codegen.java.basic.runtime.update.AbstractUpdateToolFinder;
import com.th3l4b.srm.codegen.java.basic.runtime.update.AbstractUpdateToolUpdater;
import com.th3l4b.srm.codegen.java.basic.runtime.update.IUpdateToolFinder;
import com.th3l4b.srm.codegen.java.basic.runtime.update.IUpdateToolUpdater;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractInMemoryUpdaterAndFinder implements
		IUpdateToolFinder, IUpdateToolUpdater {

	protected AbstractUpdateToolFinder _delegatedFinder = new AbstractUpdateToolFinder() {
		@Override
		protected <T extends IRuntimeEntity<T>> void findEntity(
				IRuntimeEntity<T> entity,
				HashMap<IIdentifier, IRuntimeEntity<?>> r, IModelUtils utils)
				throws Exception {
			IIdentifier id = entity.coordinates().getIdentifier();
			IRuntimeEntity<?> e = getEntities().get(id);
			if (e != null) {
				r.put(id, utils.clone(e));
			}
		}
	};

	protected AbstractUpdateToolUpdater _delegatedUpdater = new AbstractUpdateToolUpdater() {
		@Override
		protected <T extends IRuntimeEntity<T>> void updateEntity(T entity,
				T original, IModelUtils utils) throws Exception {
			IIdentifier id = entity.coordinates().getIdentifier();
			IRuntimeEntity<?> r = getEntities().get(id);

			if (r == null) {
				r = utils.create(entity.clazz());
			}
			utils.copy(entity, r, entity.clazz());
			utils.unSetNullValues(r);
			getEntities().put(id, r);
		}
	};

	protected abstract Map<IIdentifier, IRuntimeEntity<?>> getEntities()
			throws Exception;

	@Override
	public Map<IIdentifier, IRuntimeEntity<?>> find(
			Map<IIdentifier, IRuntimeEntity<?>> input, IModelUtils utils)
			throws Exception {
		return _delegatedFinder.find(input, utils);
	}

	@Override
	public void update(Map<IIdentifier, IRuntimeEntity<?>> updates,
			Map<IIdentifier, IRuntimeEntity<?>> originals, IModelUtils utils)
			throws Exception {
		_delegatedUpdater.update(updates, originals, utils);
	}
}
