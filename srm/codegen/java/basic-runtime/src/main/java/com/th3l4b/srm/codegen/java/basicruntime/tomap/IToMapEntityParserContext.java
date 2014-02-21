package com.th3l4b.srm.codegen.java.basicruntime.tomap;

import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface IToMapEntityParserContext {
	<R extends IRuntimeEntity<R>> IToMapEntityParser<R> getParser(
			Class<R> clazz) throws Exception;
}
