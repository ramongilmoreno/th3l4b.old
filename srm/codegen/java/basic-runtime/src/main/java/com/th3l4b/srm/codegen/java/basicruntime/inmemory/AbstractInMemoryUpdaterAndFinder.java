package com.th3l4b.srm.codegen.java.basicruntime.inmemory;

import java.util.HashMap;
import java.util.Map;

import com.th3l4b.srm.codegen.java.basicruntime.update.AbstractUpdateToolFinder;
import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolUpdater;
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractInMemoryUpdaterAndFinder extends
		AbstractUpdateToolFinder implements IUpdateToolUpdater {

	protected abstract Map<IIdentifier, IRuntimeEntity<?>> getEntities()
			throws Exception;

	@Override
	public void update(Map<IIdentifier, IRuntimeEntity<?>> updates,
			Map<IIdentifier, IRuntimeEntity<?>> originals, IModelUtils utils)
			throws Exception {
		for (IRuntimeEntity<?> e : updates.values()) {
			IIdentifier id = e.coordinates().getIdentifier();
			IRuntimeEntity<?> r = getEntities().get(id);

			if (r == null) {
				r = utils.create(e.clazz());
			}
			utils.copy(e, r, e.clazz());
			EntityStatus status = e.coordinates().getStatus();
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
	}

	@Override
	protected <T extends IRuntimeEntity<T>> void processEntity(
			IRuntimeEntity<T> entity,
			HashMap<IIdentifier, IRuntimeEntity<?>> r, IModelUtils utils)
			throws Exception {
		IIdentifier id = entity.coordinates().getIdentifier();
		IRuntimeEntity<?> e = getEntities().get(id);
		if (e != null) {
			r.put(id, utils.clone(e));
		}
	}
}
