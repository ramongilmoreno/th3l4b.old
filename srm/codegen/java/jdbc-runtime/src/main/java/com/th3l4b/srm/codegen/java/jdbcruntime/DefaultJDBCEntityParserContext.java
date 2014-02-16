package com.th3l4b.srm.codegen.java.jdbcruntime;

import com.th3l4b.srm.runtime.DefaultPerEntityContext;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class DefaultJDBCEntityParserContext extends
		DefaultPerEntityContext<IJDBCEntityParser<?>> implements
		IJDBCEntityParserContext {

	@SuppressWarnings("unchecked")
	@Override
	public <R extends IRuntimeEntity<R>> IJDBCEntityParser<R> getEntityParser(
			Class<R> clazz) throws Exception {
		return (IJDBCEntityParser<R>) get(clazz);
	}
}
