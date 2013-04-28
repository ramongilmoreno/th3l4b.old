package com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory;

import java.util.Iterator;

public class PredicateUtils {

	public static <S, R> Iterator<R> filter(
			final Iterator<S> iterator, final IPredicate<R> filter)
			throws Exception {
		return new Iterator<R>() {

			int _next = 0;
			R _value = null;

			@SuppressWarnings("unchecked")
			@Override
			public boolean hasNext() {
				try {
					if (_next == -1) {
						return false;
					} else if (_next == 0) {
						_value = null;
						while (iterator.hasNext()) {
							S next = iterator.next();
							if ((next != null)
									&& filter.clazz().isAssignableFrom(
											next.getClass())) {
								_value = (R) next;
							}
							if ((_value != null) && filter.accept(_value)) {
								_next = 1;
								return true;
							} else {
								_next = 0;
								_value = null;
							}
						}

						// If this point is reached, we have found the end of
						// the
						// iterator.
						_value = null;
						_next = -1;
						return false;
					} else { // A 1 value means that a data is ready
						return true;
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public R next() {
				if (hasNext()) {
					R r = _value;
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

			@Override
			public Class<T> clazz() throws Exception {
				return src.clazz();
			}

		};
	}
}
