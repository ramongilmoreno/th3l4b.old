package com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory;

import java.util.Map;

import com.th3l4b.common.data.predicate.IPredicate;
import com.th3l4b.common.data.predicate.PredicateUtils;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractInMemoryContainer {

	// Map IIdentifier, IRuntimeEntity
	protected abstract Map<IIdentifier, IRuntimeEntity> getEntities()
			throws Exception;

	@SuppressWarnings("unchecked")
	protected <T extends IRuntimeEntity> T get(Class<T> clazz,
			IIdentifier identifier) throws Exception {
		IRuntimeEntity e = getEntities().get(identifier);
		return (T) e;
	}

	@SuppressWarnings("unchecked")
	protected <T extends IRuntimeEntity> Iterable<T> find(final Class<T> clazz,
			final IPredicate<T> filter) throws Exception {
		Iterable<IRuntimeEntity> r = PredicateUtils.filter(getEntities()
				.values(), new IPredicate<IRuntimeEntity>() {
			@Override
			public boolean accept(IRuntimeEntity t) throws Exception {
				if (clazz.isAssignableFrom(t.getClass())) {
					T re = (T) t;
					return filter.accept(re);
				} else {
					return false;
				}
			}

		});
		return (Iterable<T>) r;
	}
}
