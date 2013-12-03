package com.th3l4b.srm.codegen.java.jdbcruntime.types;

import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCRuntimeType;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCRuntimeTypesContext;

public class JDBCRuntimeTypesBasicSet implements IJDBCRuntimeTypesContext {

	@Override
	public <T> IJDBCRuntimeType<T> get(String name, Class<T> clazz) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
