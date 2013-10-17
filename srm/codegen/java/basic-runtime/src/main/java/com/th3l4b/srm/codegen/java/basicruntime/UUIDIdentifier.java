package com.th3l4b.srm.codegen.java.basicruntime;

import java.util.UUID;

import com.th3l4b.srm.runtime.IIdentifier;

/**
 * Sample UUID implementation which also keeps track of the a class of an item.
 */
public class UUIDIdentifier implements IIdentifier {

	private long _msb, _lsb;

	public UUIDIdentifier() {
		UUID uuid = UUID.randomUUID();
		init(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
	}

	public UUIDIdentifier(long msb, long lsb) {
		init(msb, lsb);
	}

	public UUIDIdentifier(String s) throws NumberFormatException,
			ClassNotFoundException {
		String[] split = s.split(" ");
		init(Long.parseLong(split[0], Character.MAX_RADIX),
				Long.parseLong(split[1], Character.MAX_RADIX));
	}

	private void init(long msb, long lsb) {
		_msb = msb;
		_lsb = lsb;
	}
	
	public long getMsb() {
		return _msb;
	}
	
	public long getLsb() {
		return _lsb;
	}

	@Override
	public int hashCode() {
		long r = _msb ^ _lsb;
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
		return toString(this);
	}

	/**
	 * Returns an string suitable of being used by the
	 * {@link #UUIDIdentifier(String, ClassLoader)} constructor.
	 */
	public static String toString(UUIDIdentifier i) {
		return Long.toString(i._msb, Character.MAX_RADIX) + " "
				+ Long.toString(i._lsb, Character.MAX_RADIX);

	}

	protected static boolean eq(UUIDIdentifier a, UUIDIdentifier b) {
		if (a == b) {
			return true;
		} else if ((a == null) || (b == null)) {
			return false;
		} else {
			return (b._msb == a._msb) && (b._lsb == a._lsb);
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
