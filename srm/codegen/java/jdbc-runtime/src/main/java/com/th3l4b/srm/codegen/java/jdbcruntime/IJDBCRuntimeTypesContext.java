package com.th3l4b.srm.codegen.java.jdbcruntime;

public interface IJDBCRuntimeTypesContext {
	<T> IJDBCRuntimeType<T> get (String name, Class<T> clazz);
}
