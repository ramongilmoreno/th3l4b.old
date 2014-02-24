package com.th3l4b.srm.runtime;


public class DefaultToMapEntityParserContext extends
		DefaultPerEntityContext<IToMapEntityParser<?>> implements IToMapEntityParserContext {

	@SuppressWarnings("unchecked")
	@Override
	public <R extends IRuntimeEntity<R>> IToMapEntityParser<R> getParser(
			Class<R> clazz) throws Exception {
		return (IToMapEntityParser<R>) get(clazz);
	}

}
