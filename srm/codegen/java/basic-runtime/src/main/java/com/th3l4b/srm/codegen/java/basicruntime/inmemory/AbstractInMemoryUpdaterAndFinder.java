package com.th3l4b.srm.codegen.java.basicruntime.inmemory;

import java.util.HashMap;
import java.util.Map;

import com.th3l4b.srm.codegen.java.basicruntime.update.AbstractUpdateToolFinder;
import com.th3l4b.srm.codegen.java.basicruntime.update.AbstractUpdateToolUpdater;
import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolFinder;
import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolUpdater;
import com.th3l4b.srm.runtime.EntityStatus;
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
			EntityStatus status = entity.coordinates().getStatus();
			switch (status) {
			case Deleted:
				// No change from original status
				break;
			case Modified:
			case New:
			case Unknown:
				status = EntityStatus.Persisted;
				break;
			case Persisted:
			case Wild:
			default:
				throw new IllegalArgumentException(
						"Cannot update status of entity: " + status);
			}
			r.coordinates().setStatus(status);
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
