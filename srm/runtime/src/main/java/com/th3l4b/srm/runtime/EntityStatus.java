package com.th3l4b.srm.runtime;

import java.util.HashMap;
import java.util.Map;

public enum EntityStatus {
	Wild, New, Persisted, Modified, Deleted, Unknown;

	private static Map<String, EntityStatus> _reverse = null;

	public String initial() {
		return name().substring(0, 1);
	}

	protected static Map<String, EntityStatus> getReverse() {
		if (_reverse == null) {
			HashMap<String, EntityStatus> r = new HashMap<String, EntityStatus>();
			for (EntityStatus s : EntityStatus.values()) {
				r.put(s.initial(), s);
			}
			_reverse = r;
		}
		return _reverse;
	}

	public static EntityStatus fromInitial(String initial, EntityStatus fallback) {
		if (initial == null) {
			return fallback;
		} else {
			EntityStatus r = getReverse().get(initial);
			if (r != null) {
				return r;
			} else {
				return fallback;
			}
		}
	}
}
