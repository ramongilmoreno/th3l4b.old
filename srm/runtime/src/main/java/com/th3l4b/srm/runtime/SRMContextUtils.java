package com.th3l4b.srm.runtime;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class SRMContextUtils {

	public static Map<IIdentifier, IRuntimeEntity<?>> map(
			IRuntimeEntity<?> entity) throws Exception {
		return Collections.<IIdentifier, IRuntimeEntity<?>> singletonMap(entity
				.coordinates().getIdentifier(), entity);
	}

	public static Map<IIdentifier, IRuntimeEntity<?>> map(
			Iterable<IRuntimeEntity<?>> entities) throws Exception {
		LinkedHashMap<IIdentifier, IRuntimeEntity<?>> r = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		for (IRuntimeEntity<?> entity : entities) {
			r.put(entity.coordinates().getIdentifier(), entity);
		}
		return r;
	}

}
