package com.th3l4b.srm.codegen.java.basicruntime.update;

import java.util.LinkedHashMap;
import java.util.Map;

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
				filtered.put(e.coordinates().getIdentifier(), e);
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

		// Proceed with the update
		updater.update(filtered, originals, utils);
	}

}
