package com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolFinder;
import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolUpdater;
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractInMemoryUpdaterAndFinder implements
		IUpdateToolUpdater, IUpdateToolFinder {

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
	public Map<IIdentifier, IRuntimeEntity<?>> find(
			Map<IIdentifier, IRuntimeEntity<?>> input, IModelUtils utils)
			throws Exception {

		LinkedHashMap<IIdentifier, IRuntimeEntity<?>> r = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		Map<IIdentifier, IRuntimeEntity<?>> src = getEntities();
		for (IIdentifier id : input.keySet()) {
			IRuntimeEntity<?> e = src.get(id);
			if (e != null) {
				r.put(id, utils.clone(e));
			}
		}
		return r;
	}
}
