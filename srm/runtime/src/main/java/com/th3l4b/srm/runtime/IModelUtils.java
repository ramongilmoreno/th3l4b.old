package com.th3l4b.srm.runtime;

public interface IModelUtils {
	<T extends IRuntimeEntity<?>> IIdentifier identifier(Class<T> clazz, Object... args)
			throws Exception;

	boolean compare(IIdentifier a, IIdentifier b) throws Exception;

	<T extends IRuntimeEntity<?>> T create(Class<T> clazz) throws Exception;
	
	<T extends IRuntimeEntity<?>> T clone(T src) throws Exception;
	
	<T extends IRuntimeEntity<?>> void copy(Object source, Object target,
			Class<T> clazz) throws Exception;
}
