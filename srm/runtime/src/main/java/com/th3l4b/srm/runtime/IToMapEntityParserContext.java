package com.th3l4b.srm.runtime;

public interface IToMapEntityParserContext {
	<T extends IRuntimeEntity<?>> IToMapEntityParser<T> getParser(Class<T> clazz)
			throws Exception;
}
