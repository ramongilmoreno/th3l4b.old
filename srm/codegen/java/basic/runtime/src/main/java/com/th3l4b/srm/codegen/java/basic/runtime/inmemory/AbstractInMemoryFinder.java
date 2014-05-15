package com.th3l4b.srm.codegen.java.basic.runtime.inmemory;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.codegen.java.basic.runtime.DefaultIdentifier;
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractInMemoryFinder {

	protected Map<Pair, AbstractPredicateOfRelationship<?, ?>> _map = new LinkedHashMap<Pair, AbstractPredicateOfRelationship<?, ?>>();
	private IModelUtils _utils;

	public AbstractInMemoryFinder(IModelUtils utils) {
		_utils = utils;
	}

	// Map IIdentifier, IRuntimeEntity
	protected abstract Map<IIdentifier, IRuntimeEntity<?>> getEntities()
			throws Exception;

	protected IModelUtils getModelUtils() throws Exception {
		return _utils;
	}

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
		DefaultIdentifier.checkIdentifierType(sourceIdentifier, sourceClass);
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
		DefaultIdentifier.checkIdentifierType(identifier, clazz);
		IRuntimeEntity<?> t = getEntities().get(identifier);
		if (t == null) {
			T r = getModelUtils().create(clazz);
			ICoordinates coordinates = r.coordinates();
			coordinates.setIdentifier(identifier);
			coordinates.setStatus(EntityStatus.Unknown);
			return r;
		} else if (!clazz.isAssignableFrom(t.clazz())) {
			throw new IllegalStateException("Found an object: " + t
					+ ", but expected an object of class: " + clazz);
		} else {
			return (T) t;
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
