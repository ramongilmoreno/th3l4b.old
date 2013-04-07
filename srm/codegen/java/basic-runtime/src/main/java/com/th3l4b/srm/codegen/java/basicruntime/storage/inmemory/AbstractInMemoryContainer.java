package com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory;

import java.util.Map;

import com.th3l4b.common.data.predicate.IPredicate;
import com.th3l4b.common.data.predicate.PredicateUtils;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractInMemoryContainer {

	// Map IIdentifier, IRuntimeEntity
	protected abstract Map<IIdentifier, IRuntimeEntity<?>> getEntities()
			throws Exception;

	@SuppressWarnings("unchecked")
	protected <T2 extends IRuntimeEntity<T2>> T2 get(Class<T2> clazz,
			IIdentifier identifier) throws Exception {
		IRuntimeEntity<?> t = getEntities().get(identifier);
		if (t == null) {
			return null;
		}

		if (clazz.isAssignableFrom(t.clazz())) {
			return (T2) t;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected <T2 extends IRuntimeEntity<T2>> Iterable<T2> find(
			final Class<T2> clazz, final IPredicate<T2> filter)
			throws Exception {
		Iterable<? extends IRuntimeEntity<?>> r = PredicateUtils.filter(
				getEntities().values(), new IPredicate<IRuntimeEntity<?>>() {
					@Override
					public boolean accept(IRuntimeEntity<?> t) throws Exception {
						if (clazz.isAssignableFrom(t.clazz())) {
							return filter.accept((T2) t);
						} else {
							return false;
						}
					}
				});
		return (Iterable<T2>) r;
	}
}
