package com.th3l4b.srm.codegen.java.jdbc.runtime;

public interface IJDBCRuntimeTypesContext {
	<T> IJDBCRuntimeType<T> get (String name, Class<T> clazz);
}
