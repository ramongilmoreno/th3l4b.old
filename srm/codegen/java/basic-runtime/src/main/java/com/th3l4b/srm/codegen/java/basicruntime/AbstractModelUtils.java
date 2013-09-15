package com.th3l4b.srm.codegen.java.basicruntime;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

/**
 * An {@link IModelUtils} implementation built around the {@link UUIDIdentifier}
 * class for identifiers.
 */
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
	public <T extends IRuntimeEntity<?>> IIdentifier identifier(Class<T> clazz,
			Object... args) throws Exception {
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

	@Override
	public String identifierToString(IIdentifier id) throws Exception {
		return UUIDIdentifier.toString((UUIDIdentifier) id);
	}

	@Override
	public IIdentifier identifierFromString(String id) throws Exception {
		return new UUIDIdentifier(id, whichClassLoader());
	}

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

	/**
	 * This utility allows specifying different class loaders. This default
	 * implementation class {@link #getClass()} then
	 * {@link Class#getClassLoader()}. Usually this implementation get an
	 * appropriate class loader from the non abstract class that instantiates
	 * this object, but in the case this is not enough, this method allows
	 * overriding that behavior.
	 */
	protected ClassLoader whichClassLoader() throws Exception {
		return getClass().getClassLoader();
	}

	@Override
	public <T extends IRuntimeEntity<?>> T clone(T source) throws Exception {
		@SuppressWarnings("unchecked")
		T r = (T) create(source.clazz());
		_copiers.get(source.clazz().getName()).copy(source, r);
		return r;
	}
}
