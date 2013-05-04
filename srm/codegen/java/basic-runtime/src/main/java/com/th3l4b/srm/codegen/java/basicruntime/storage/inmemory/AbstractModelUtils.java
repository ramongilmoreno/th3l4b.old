package com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractModelUtils implements IModelUtils {

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

	protected static <T extends IRuntimeEntity<T>> T initialize(Class<T> clazz,
			T entity) throws Exception {
		ICoordinates coordinates = entity.coordinates();
		coordinates.setIdentifier(new UUIDIdentifier(clazz));
		coordinates.setStatus(EntityStatus.New);
		return entity;
	}

	protected Map<String, Creator> _creators = new LinkedHashMap<String, Creator>();
	protected Map<String, Copier<?>> _copiers = new LinkedHashMap<String, Copier<?>>();

	@Override
	public <T2 extends IRuntimeEntity<T2>> IIdentifier identifier(
			Class<T2> clazz, Object... args) throws Exception {
		Long msb = (Long) args[0];
		Long lsb = (Long) args[1];
		return new UUIDIdentifier(clazz, msb.longValue(), lsb.longValue());
	}

	@Override
	public boolean compare(IIdentifier a, IIdentifier b) throws Exception {
		return compareStatic(a, b);
	}

	public static boolean compareStatic(IIdentifier a, IIdentifier b)
			throws Exception {
		return UUIDIdentifier.eq((UUIDIdentifier) a, (UUIDIdentifier) b);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T2 extends IRuntimeEntity<T2>> T2 create(Class<T2> clazz)
			throws Exception {
		Creator creator = _creators.get(clazz.getName());
		if (creator == null) {
			throw new IllegalArgumentException("Unknown class: "
					+ clazz.getName());
		}
		return (T2) creator.create();
	}

	@Override
	public <T2 extends IRuntimeEntity<T2>> void copy(Object source,
			Object target, Class<T2> clazz) throws Exception {
		_copiers.get(clazz.getName()).copy(source, target);
	}

}
