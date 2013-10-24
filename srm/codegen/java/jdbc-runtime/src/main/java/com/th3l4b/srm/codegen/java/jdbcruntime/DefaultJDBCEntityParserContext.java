package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.runtime.IRuntimeEntity;

public class DefaultJDBCEntityParserContext implements IJDBCEntityParserContext {

	protected Map<String, IJDBCEntityParser<?>> _parsers = new LinkedHashMap<String, IJDBCEntityParser<?>>();
	
	@SuppressWarnings("unchecked")
	public <R extends IRuntimeEntity<R>> IJDBCEntityParser<R> getEntityParser(
			Class<R> clazz) throws Exception {
		return (IJDBCEntityParser<R>) _parsers.get(clazz.getName());
	}

	public <R extends IRuntimeEntity<R>> void putEntityParser(
			IJDBCEntityParser<R> parser, Class<R> clazz) throws Exception {
		_parsers.put(clazz.getName(), parser);
	}

}
