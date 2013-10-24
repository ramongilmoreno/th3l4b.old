package com.th3l4b.srm.codegen.java.jdbcruntime;

import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface IJDBCEntityParserContext {
	<R extends IRuntimeEntity<R>> IJDBCEntityParser<R> getEntityParser(
			Class<R> clazz) throws Exception;
}
