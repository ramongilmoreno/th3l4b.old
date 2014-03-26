package com.th3l4b.srm.runtime;

public class DefaultToMapEntityParserContext extends
		DefaultPerEntityContext<IToMapEntityParser<?>> implements
		IToMapEntityParserContext {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IRuntimeEntity<?>> IToMapEntityParser<T> getParser(
			Class<T> clazz) throws Exception {
		return (IToMapEntityParser<T>) get(clazz);
	}

}
