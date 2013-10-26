package com.th3l4b.srm.codegen.java.basicruntime.inmemory;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractInMemoryFinder {

	protected Map<Pair, AbstractPredicateOfRelationship<?, ?>> _map = new LinkedHashMap<Pair, AbstractPredicateOfRelationship<?, ?>>();

	// Map IIdentifier, IRuntimeEntity
	protected abstract Map<IIdentifier, IRuntimeEntity<?>> getEntities()
			throws Exception;

	@SuppressWarnings("unchecked")
	protected <R extends IRuntimeEntity<R>, S extends IRuntimeEntity<S>> AbstractPredicateOfRelationship<R, S> getPredicateForRelationship(
			Class<R> resultClass, Class<S> sourceClass,
			IIdentifier sourceIdentifier, String relationship) {
		return (AbstractPredicateOfRelationship<R, S>) _map.get(new Pair(
				sourceClass.getName(), relationship));
	}

	public <R extends IRuntimeEntity<R>, S extends IRuntimeEntity<S>> Iterable<R> find(
			Class<R> resultClass, Class<S> sourceClass,
			IIdentifier sourceIdentifier, String relationship) throws Exception {
		final IPredicate<R> p = getPredicateForRelationship(resultClass,
				sourceClass, sourceIdentifier, relationship).predicate(
				sourceIdentifier);

		return new Iterable<R>() {
			@Override
			public Iterator<R> iterator() {
				try {
					return PredicateUtils.filterPersistedOnly(getEntities()
							.values().iterator(), p);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

		};
	}

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

	public <T extends IRuntimeEntity<T>> Iterable<T> all(final Class<T> clazz)
			throws Exception {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				try {
					return PredicateUtils.filterPersistedOnly(getEntities()
							.values().iterator(), new IPredicate<T>() {
						@Override
						public boolean accept(T arg) throws Exception {
							return true;
						}

						@Override
						public Class<T> clazz() throws Exception {
							return clazz;
						}

					});
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			}

		};
	}

}
