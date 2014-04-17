package com.th3l4b.srm.codegen.java.mongoruntime;

import com.th3l4b.srm.runtime.DefaultPerEntityContext;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class DefaultMongoEntityParserContext extends
		DefaultPerEntityContext<IMongoEntityParser<?>> implements
		IMongoEntityParserContext {

	@SuppressWarnings("unchecked")
	@Override
	public <R extends IRuntimeEntity<R>> IMongoEntityParser<R> getEntityParser(
			Class<R> clazz) throws Exception {
		return (IMongoEntityParser<R>) get(clazz);
	}
}
