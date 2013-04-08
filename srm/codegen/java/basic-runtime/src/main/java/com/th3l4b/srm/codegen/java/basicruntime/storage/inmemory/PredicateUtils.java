package com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory;

import java.util.Iterator;

public class PredicateUtils {
	public static <T> Iterator<T> filter(final Iterator<T> iterator,
			final IPredicate<T> filter) throws Exception {
		return new Iterator<T>() {

			int _next = 0;
			T _value = null;

			@Override
			public boolean hasNext() {
				if (_next == -1) {
					return false;
				} else if (_next == 0) {
					_value = null;
					while (iterator.hasNext()) {
						_value = iterator.next();
						try {
							if (filter.accept(_value)) {
								_next = 1;
								return true;
							} else {
								_next = 0;
								_value = null;
							}
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}

					// If this point is reached, we have found the end of the
					// iterator.
					_value = null;
					_next = -1;
					return false;
				} else { // A 1 value means that a data is ready
					return true;
				}
			}

			@Override
			public T next() {
				if (hasNext()) {
					T r = _value;
					_next = 0;
					_value = null;
					return r;
				} else {
					throw new IllegalStateException();
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	public static <T> Iterable<T> filter(final Iterable<T> iterable,
			final IPredicate<T> filter) throws Exception {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				try {
					return filter(iterable.iterator(), filter);
				} catch (Exception e) {
					throw new RuntimeException();
				}
			}
		};
	}

	public static <T> IPredicate<T> not(final IPredicate<T> src) {
		return new IPredicate<T>() {
			@Override
			public boolean accept(T t) throws Exception {
				return !src.accept(t);
			}

		};
	}
}
