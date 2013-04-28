package com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory;

public class Pair {
	String _clazz;
	String _relationship;

	public Pair(String clazz, String relationship) {
		_clazz = clazz;
		_relationship = relationship;
	}

	@Override
	public int hashCode() {
		return _clazz.hashCode() ^ _relationship.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Pair p = (Pair) obj;
		return p._clazz.equals(_clazz) && p._relationship.equals(_relationship);
	}
}
