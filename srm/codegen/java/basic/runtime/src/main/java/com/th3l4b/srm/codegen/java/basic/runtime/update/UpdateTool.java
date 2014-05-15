package com.th3l4b.srm.codegen.java.basic.runtime.update;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class UpdateTool {

	public static void process(Map<IIdentifier, IRuntimeEntity<?>> entities,
			IUpdateToolFinder finder, IUpdateToolUpdater updater,
			IModelUtils utils) throws Exception {
		// Filter non updatables
		LinkedHashMap<IIdentifier, IRuntimeEntity<?>> filtered = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();

		for (IRuntimeEntity<?> e : entities.values()) {
			EntityStatus status = e.coordinates().getStatus();
			if (status.isTransitional()
					&& !NullSafe.equals(status, EntityStatus.Ignore)) {
				IRuntimeEntity<?> clone = utils.clone(e);
				EntityStatus newStatus = null;
				// Change the status accordingly
				switch (status) {
				case Modify:
					newStatus = EntityStatus.Persisted;
					break;
				case Remove:
					newStatus = EntityStatus.Deleted;
					break;
				case Deleted:
				case Persisted:
				case Unknown:
				case Ignore:
					throw new IllegalStateException(
							"Unsupported status in this phase: " + status);
				}
				clone.coordinates().setStatus(newStatus);
				filtered.put(e.coordinates().getIdentifier(), clone);
			}
		}

		// Get the originals
		Map<IIdentifier, IRuntimeEntity<?>> originals = finder.find(filtered,
				utils);

		// Proceed with update
		updater.update(filtered, originals, utils);
	}
}
