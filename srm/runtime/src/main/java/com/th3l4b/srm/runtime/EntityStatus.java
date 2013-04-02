package com.th3l4b.srm.runtime;

public enum EntityStatus {
	Wild, New, Persisted, Modified, Deleted, Unknown;
	public char initial () {
		return name().charAt(0);
	}
}
