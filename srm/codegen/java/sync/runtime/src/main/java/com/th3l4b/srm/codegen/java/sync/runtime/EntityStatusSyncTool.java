package com.th3l4b.srm.codegen.java.sync.runtime;

import static com.th3l4b.srm.runtime.EntityStatus.Deleted;
import static com.th3l4b.srm.runtime.EntityStatus.Modify;
import static com.th3l4b.srm.runtime.EntityStatus.Persisted;
import static com.th3l4b.srm.runtime.EntityStatus.Remove;
import static com.th3l4b.srm.runtime.EntityStatus.Unknown;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.common.data.Pair;
import com.th3l4b.srm.runtime.EntityStatus;

public class EntityStatusSyncTool {

	private static class P extends Pair<EntityStatus, EntityStatus> {
		public P(EntityStatus from, EntityStatus to) {
			super(from, to);
		}
	}

	private static LinkedHashMap<P, EntityStatus> _map;

	private static Map<P, EntityStatus> map() {
		if (_map == null) {
			LinkedHashMap<P, EntityStatus> map = new LinkedHashMap<P, EntityStatus>();
			// Ensure all cases are covered
			// Wild, New, Persisted, Modified, Deleted, Unknown;
			map.put(new P(Persisted, Persisted), Modify);
			map.put(new P(Persisted, Deleted), Remove);
			map.put(new P(Persisted, Unknown), Modify);
			map.put(new P(Deleted, Persisted), Modify);
			map.put(new P(Deleted, Deleted), Remove);
			map.put(new P(Deleted, Unknown), Remove);
			map.put(new P(Unknown, Persisted), Modify);
			map.put(new P(Unknown, Deleted), Remove);
			map.put(new P(Unknown, Unknown), Modify);
			_map = map;
		}
		return _map;
	}

	public static EntityStatus howToGet(EntityStatus from, EntityStatus to)
			throws Exception {
		P transition = new P(from, to);
		Map<P, EntityStatus> map = map();
		if (!map.containsKey(transition)) {
			throw new IllegalStateException(
					"Mapping not found for transition: " + transition);
		} else {
			return map.get(transition);
		}
	}
}
