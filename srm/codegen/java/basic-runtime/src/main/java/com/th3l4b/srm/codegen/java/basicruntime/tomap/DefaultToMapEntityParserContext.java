package com.th3l4b.srm.codegen.java.basicruntime.tomap;

import com.th3l4b.srm.runtime.DefaultPerEntityContext;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class DefaultToMapEntityParserContext extends
		DefaultPerEntityContext<IToMapEntityParser<?>> implements IToMapEntityParserContext {

	@SuppressWarnings("unchecked")
	@Override
	public <R extends IRuntimeEntity<R>> IToMapEntityParser<R> getParser(
			Class<R> clazz) throws Exception {
		return (IToMapEntityParser<R>) get(clazz);
	}

}
