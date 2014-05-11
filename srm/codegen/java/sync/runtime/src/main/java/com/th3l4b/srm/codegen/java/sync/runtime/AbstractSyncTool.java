package com.th3l4b.srm.codegen.java.sync.runtime;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IFinder;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.srm.runtime.ISRMContext;

public abstract class AbstractSyncTool<FINDER extends IFinder, CONTEXT extends ISRMContext<FINDER>> {

	protected abstract CONTEXT getContext() throws Exception;

	protected abstract IEntityDiffContext getDiffContext() throws Exception;

	/**
	 * Takes some entities changed elsewhere and computes the required changes
	 * to the context {@link #getContext()} to persist those changes.
	 * 
	 * @param changes
	 *            The changes to receive
	 * @return The modifications to apply
	 * @throws Exception
	 */
	public Map<IIdentifier, IRuntimeEntity<?>> sync(
			Map<IIdentifier, IRuntimeEntity<?>> changes) throws Exception {
		Map<IIdentifier, IRuntimeEntity<?>> r = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();

		for (IRuntimeEntity<?> entity : changes.values()) {
			handle(entity, r);
		}
		return r;
	}

	@SuppressWarnings("unchecked")
	public <T extends IRuntimeEntity<T>> void handle(IRuntimeEntity<T> entity,
			Map<IIdentifier, IRuntimeEntity<?>> r) throws Exception {
		Class<T> clazz = entity.clazz();
		CONTEXT context = getContext();
		ICoordinates entityCoordinates = entity.coordinates();
		T found = context.getFinder().find(clazz,
				entityCoordinates.getIdentifier());
		T diff = context.getUtils().create(clazz);
		boolean diffs = getDiffContext().getEntityDiff(clazz).diff(found,
				(T) entity, diff);
		if (diffs) {
			// Apply diffs
			r.put(diff.coordinates().getIdentifier(), diff);
		}
	}

}
