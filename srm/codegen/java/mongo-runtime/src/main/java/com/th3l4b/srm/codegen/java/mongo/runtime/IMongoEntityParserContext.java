package com.th3l4b.srm.codegen.java.mongoruntime;

import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface IMongoEntityParserContext {
	<R extends IRuntimeEntity<R>> IMongoEntityParser<R> getEntityParser(
			Class<R> clazz) throws Exception;
}
