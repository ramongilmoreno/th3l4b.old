package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface IAndroidSQLiteEntityParserContext {
	<R extends IRuntimeEntity<R>> IAndroidSQLiteEntityParser<R> getEntityParser(
			Class<R> clazz) throws Exception;
}