package com.th3l4b.common.data;

import com.th3l4b.common.data.nullsafe.NullSafe;

public class Pair<A, B> {

	protected A _a;
	protected B _b;

	public Pair() {
	}

	public Pair(A a, B b) {
		_a = a;
		_b = b;
	}

	public A getA() {
		return _a;
	}

	public void setA(A a) {
		_a = a;
	}

	public B getB() {
		return _b;
	}

	public void setB(B b) {
		_b = b;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pair<?, ?>) {
			Pair<?, ?> p = (Pair<?, ?>) obj;
			Object obja = p.getA();
			Object a = getA();
			if (!NullSafe.equals(a, obja)) {
				return false;
			}
			Object objb = p.getB();
			Object b = getB();
			if (!NullSafe.equals(b, objb)) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return NullSafe.hashCode(getA()) ^ NullSafe.hashCode(getB());
	}

	@Override
	public String toString() {
		return "[" + getA() + ", " + getB() + "]";
	}

}
