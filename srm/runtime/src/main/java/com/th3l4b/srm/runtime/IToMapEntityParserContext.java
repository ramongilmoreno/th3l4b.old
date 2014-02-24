package com.th3l4b.srm.runtime;


public interface IToMapEntityParserContext {
	<R extends IRuntimeEntity<R>> IToMapEntityParser<R> getParser(
			Class<R> clazz) throws Exception;
}
