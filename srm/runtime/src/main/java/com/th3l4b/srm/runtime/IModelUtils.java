package com.th3l4b.srm.runtime;

public interface IModelUtils {
	boolean compare (IIdentifier a, IIdentifier b) throws Exception;
	<T extends IRuntimeEntity> T create(Class<T> clazz) throws Exception;
	<T extends IRuntimeEntity, R> R getValue (T entity, String field, Class<R> clazz) throws Exception;
	<T extends IRuntimeEntity, R> void setValue (T entity, String field, R value) throws Exception;
}
