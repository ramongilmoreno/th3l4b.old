package com.th3l4b.srm.codegen.java.basicruntime.update;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class UpdateTool {

	public static void process(Map<IIdentifier, IRuntimeEntity<?>> entities,
			IUpdateToolFinder finder, IUpdateToolUpdater updater,
			IModelUtils utils) throws Exception {
		// Filter non updatable
		LinkedHashMap<IIdentifier, IRuntimeEntity<?>> filtered = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		for (IRuntimeEntity<?> e : entities.values()) {
			switch (e.coordinates().getStatus()) {
			case Deleted:
			case Modified:
			case New:
			case Unknown:
				filtered.put(e.coordinates().getIdentifier(), utils.clone(e));
				break;
			case Persisted:
			case Wild:
			default:
				break;
			}
		}

		// Get the originals
		Map<IIdentifier, IRuntimeEntity<?>> originals = finder.find(filtered,
				utils);

		// Resolve unknown status
		for (IRuntimeEntity<?> e : entities.values()) {
			if (e.coordinates().getStatus() == EntityStatus.Unknown) {
				EntityStatus status = EntityStatus.New;
				if (originals.containsKey(e.coordinates().getIdentifier())) {
					status = EntityStatus.Modified;
				}
				e.coordinates().setStatus(status);
			}
		}

		// Prevent cycles by creating the new entities first. Then apply the
		// update on a second pass.
		LinkedHashMap<IIdentifier, IRuntimeEntity<?>> seconds = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		for (IRuntimeEntity<?> e : entities.values()) {
			if (e.coordinates().getStatus() == EntityStatus.New) {
				// Keep original values in a separate list to modify them later.
				IRuntimeEntity<?> second = utils.clone(e);
				second.coordinates().setStatus(EntityStatus.Modified);
				seconds.put(e.coordinates().getIdentifier(), second);

				// Clear foreign keys on first pass entities
				utils.clearForeignKeys(e);
			}
		}

		// Proceed with first pass update
		updater.update(filtered, originals, utils);

		// Get the items for the second pass
		Map<IIdentifier, IRuntimeEntity<?>> secondOriginals = finder.find(
				seconds, utils);
		updater.update(seconds, secondOriginals, utils);
	}
}
