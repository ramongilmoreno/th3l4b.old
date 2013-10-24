package com.th3l4b.types.runtime;

public interface IJDBCRuntimeTypesContext {
	<T> IJDBCRuntimeType<T> get (String name, Class<T> clazz);
}
