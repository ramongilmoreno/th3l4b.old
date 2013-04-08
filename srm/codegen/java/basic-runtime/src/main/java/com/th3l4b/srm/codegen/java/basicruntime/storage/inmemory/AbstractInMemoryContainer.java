package com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory;

import java.util.Map;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractInMemoryContainer {

	protected abstract class Predicate<T> {
		IIdentifier _target;

		public Predicate(IIdentifier target) {
			_target = target;
		}

		protected abstract IIdentifier getTarget(T src) throws Exception;

		@SuppressWarnings("unchecked")
		public boolean accept(Object o) throws Exception {
			return AbstractModelUtils.compareStatic(getTarget((T) o), _target);
		}
	};

	// Map IIdentifier, IRuntimeEntity
	protected abstract Map<IIdentifier, IRuntimeEntity<?>> getEntities()
			throws Exception;

	@SuppressWarnings("unchecked")
	public <T extends IRuntimeEntity<T>> T find(Class<T> clazz,
			IIdentifier identifier) throws Exception {
		IRuntimeEntity<?> t = getEntities().get(identifier);
		if (t == null) {
			return null;
		}

		if (clazz.isAssignableFrom(t.clazz())) {
			return (T) t;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected <T extends IRuntimeEntity<T>> Iterable<T> find(
			final Class<T> clazz, final IPredicate<T> filter) throws Exception {
		Iterable<? extends IRuntimeEntity<?>> r = PredicateUtils.filter(
				getEntities().values(), new IPredicate<IRuntimeEntity<?>>() {
					@Override
					public boolean accept(IRuntimeEntity<?> t) throws Exception {
						if (clazz.isAssignableFrom(t.clazz())) {
							return filter.accept((T) t);
						} else {
							return false;
						}
					}
				});
		return (Iterable<T>) r;
	}
}
