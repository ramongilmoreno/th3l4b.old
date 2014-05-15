package com.th3l4b.srm.codegen.java.basic.runtime.update;

import java.util.Map;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractUpdateToolUpdater implements IUpdateToolUpdater {

	@Override
	public void update(Map<IIdentifier, IRuntimeEntity<?>> updates,
			Map<IIdentifier, IRuntimeEntity<?>> originals, IModelUtils utils)
			throws Exception {
		for (IRuntimeEntity<?> entity : updates.values()) {
			IRuntimeEntity<?> original = originals.get(entity.coordinates()
					.getIdentifier());
			updateEntityInternal(entity, original, utils);
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends IRuntimeEntity<T>> void updateEntityInternal(
			Object entity, Object original, IModelUtils utils) throws Exception {
		updateEntity((T) entity, (T) original, utils);
	}

	protected abstract <T extends IRuntimeEntity<T>> void updateEntity(
			T entity, T original, IModelUtils utils) throws Exception;
}
