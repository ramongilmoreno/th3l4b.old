package com.th3l4b.srm.codegen.java.sync.runtime;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractEntityDiff<T extends IRuntimeEntity<T>>
		implements IEntityDiff<T> {

	@Override
	public boolean diff(T from, T to, T diff) throws Exception {
		ICoordinates toCoordinates = to.coordinates();
		EntityStatus toStatus = toCoordinates.getStatus();

		// Compute diff coordinates
		ICoordinates diffCoordinates = diff.coordinates();
		diffCoordinates.setIdentifier(toCoordinates.getIdentifier());
		EntityStatus diffStatus = EntityStatusSyncTool.howToGet(from
				.coordinates().getStatus(), toStatus);
		diffCoordinates.setStatus(diffStatus);

		// Apply diffs to diff
		boolean valuesChanged = diffRest(from, to, diff);

		// Tell if any change is required: both in data or in status
		return NullSafe.equals(from.coordinates().getStatus(), toStatus)
				|| valuesChanged;
	}

	protected abstract boolean diffRest(T from, T to, T diff) throws Exception;

}
