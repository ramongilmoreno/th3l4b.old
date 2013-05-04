package com.th3l4b.srm.runtime;

public interface IModelUtils {
	<T2 extends IRuntimeEntity<T2>> IIdentifier identifier(Class<T2> clazz, Object... args)
			throws Exception;

	boolean compare(IIdentifier a, IIdentifier b) throws Exception;

	<T2 extends IRuntimeEntity<T2>> T2 create(Class<T2> clazz) throws Exception;
	
	<T2 extends IRuntimeEntity<T2>> void copy(Object source, Object target, Class<T2> clazz) throws Exception;
}
