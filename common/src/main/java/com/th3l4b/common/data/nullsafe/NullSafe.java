package com.th3l4b.common.data.nullsafe;

import java.util.Comparator;

import com.th3l4b.common.data.IGetter;

public class NullSafe {

	private static Comparator<Object> DEFAULT;

	private static Comparator<Object> getDefaultComparator() {
		if (DEFAULT == null) {
			DEFAULT = new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					if (o1.equals(o2) && o2.equals(o1)) {
						return 0;
					} else {
						return -1;
					}
				}
			};
		}

		return DEFAULT;
	}

	public static boolean equals(Object a, Object b) {
		return compare(a, b, getDefaultComparator()) == 0;
	}

	public static <T> int compare(T a, T b, Comparator<T> comparator) {
		if (a == b) {
			return 0;
		} else if (a == null) {
			return 1;
		} else if (b == null) {
			return -1;
		} else {
			return comparator.compare(a, b);
		}
	}

	public static <R, S> R get(S source, IGetter<R, S> getter) throws Exception {
		if (source != null) {
			return getter.get(source);
		} else {
			return null;
		}
	}

	public static <R, S> IGetter<R, S> getter(final IGetter<R, S> getter) {
		return new IGetter<R, S>() {
			@Override
			public R get(S source) throws Exception {
				return NullSafe.get(source, getter);
			}

		};

	}
}
