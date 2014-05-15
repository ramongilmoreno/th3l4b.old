package com.th3l4b.srm.codegen.java.basic.runtime;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

/**
 * An {@link IModelUtils} implementation built around the {@link UUIDIdentifier}
 * class for identifiers.
 */
public abstract class AbstractModelUtils implements IModelUtils {

	protected Map<String, Class<? extends IRuntimeEntity<?>>> _classFromName = new HashMap<String, Class<? extends IRuntimeEntity<?>>>();
	protected Map<Class<? extends IRuntimeEntity<?>>, String> _nameFromClass = new HashMap<Class<? extends IRuntimeEntity<?>>, String>();

	protected <T extends IRuntimeEntity<?>> void register(String name,
			Class<T> clazz) {
		_classFromName.put(name, clazz);
		_nameFromClass.put(clazz, name);
	}

	@Override
	public <T extends IRuntimeEntity<?>> String nameFromClass(Class<T> clazz)
			throws Exception {
		return _nameFromClass.get(clazz);
	}

	public Class<? extends IRuntimeEntity<?>> classFromName(String name)
			throws Exception {
		return _classFromName.get(name);
	}

	protected interface Creator {
		Object create() throws Exception;
	}

	protected static abstract class Copier<T extends IRuntimeEntity<T>> {
		@SuppressWarnings("unchecked")
		public void copy(Object source, Object target) throws Exception {
			copyEntity((T) source, (T) target);
		}

		protected abstract void copyEntity(T source, T target) throws Exception;
	}

	protected static abstract class ForeignKeysClearer<T extends IRuntimeEntity<T>> {
		@SuppressWarnings("unchecked")
		public void clear(Object obj) throws Exception {
			clearEntity((T) obj);
		}

		protected abstract void clearEntity(T obj) throws Exception;
	}

	protected static <T extends IRuntimeEntity<T>> T initialize(Class<T> clazz,
			T entity) throws Exception {
		ICoordinates coordinates = entity.coordinates();
		coordinates.setIdentifier(new DefaultIdentifier(clazz));
		coordinates.setStatus(EntityStatus.Modify);
		return entity;
	}

	protected Map<String, Creator> _creators = new LinkedHashMap<String, Creator>();
	protected Map<String, Copier<?>> _copiers = new LinkedHashMap<String, Copier<?>>();
	protected Map<String, ForeignKeysClearer<?>> _resetters = new LinkedHashMap<String, ForeignKeysClearer<?>>();

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IRuntimeEntity<?>> T create(Class<T> clazz)
			throws Exception {
		Creator creator = _creators.get(clazz.getName());
		if (creator == null) {
			throw new IllegalArgumentException("Unknown class: "
					+ clazz.getName());
		}
		return (T) creator.create();
	}

	@Override
	public <T extends IRuntimeEntity<?>> void copy(IRuntimeEntity<?> source,
			IRuntimeEntity<?> target, Class<T> clazz) throws Exception {
		_copiers.get(clazz.getName()).copy(source, target);
		target.coordinates()
				.setIdentifier(source.coordinates().getIdentifier());
		target.coordinates().setStatus(source.coordinates().getStatus());
	}

	@Override
	public <T extends IRuntimeEntity<?>> T clone(T source) throws Exception {
		@SuppressWarnings("unchecked")
		T r = (T) create(source.clazz());
		_copiers.get(source.clazz().getName()).copy(source, r);
		return r;
	}

	@Override
	public <T extends IRuntimeEntity<?>> void clearForeignKeys(T obj)
			throws Exception {
		_resetters.get(obj.clazz().getName()).clear(obj);
	}
}
