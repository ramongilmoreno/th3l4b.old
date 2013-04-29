package com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory;

import java.util.UUID;

import com.th3l4b.srm.runtime.IIdentifier;

public class UUIDIdentifier implements IIdentifier {

	private Class<?> _clazz;
	private long _msb, _lsb;

	public UUIDIdentifier(Class<?> clazz) {
		UUID uuid = UUID.randomUUID();
		init(clazz, uuid.getMostSignificantBits(),
				uuid.getLeastSignificantBits());
	}

	public UUIDIdentifier(Class<?> clazz, long msb, long lsb) {
		init(clazz, msb, lsb);
	}

	private void init(Class<?> clazz, long msb, long lsb) {
		if (clazz == null) {
			throw new NullPointerException("Class cannot be null");
		}
		_clazz = clazz;
		_msb = msb;
		_lsb = lsb;
	}

	@Override
	public int hashCode() {
		long r = ((long) _clazz.hashCode()) ^ _msb ^ _lsb;
		return new Long(r).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UUIDIdentifier) {
			UUIDIdentifier b = (UUIDIdentifier) obj;
			return eq(this, b);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return _clazz.getSimpleName() + " - " + new UUID(_msb, _lsb).toString();
	}

	protected static boolean eq(UUIDIdentifier a, UUIDIdentifier b) {
		if (a == b) {
			return true;
		} else if ((a == null) || (b == null)) {
			return false;
		} else {
			return b._clazz.getName().equals(a._clazz.getName())
					&& (b._msb == a._msb) && (b._lsb == a._lsb);
		}
	}

	public static boolean equals(Object a, Object b) {
		if (a == b) {
			return true;
		} else if ((a == null) || (b == null)) {
			return false;
		} else {
			if ((a instanceof UUIDIdentifier) && (b instanceof UUIDIdentifier)) {
				UUIDIdentifier ida = (UUIDIdentifier) a;
				UUIDIdentifier idb = (UUIDIdentifier) b;
				return eq(ida, idb);
			} else {
				return false;
			}
		}
	}

}
