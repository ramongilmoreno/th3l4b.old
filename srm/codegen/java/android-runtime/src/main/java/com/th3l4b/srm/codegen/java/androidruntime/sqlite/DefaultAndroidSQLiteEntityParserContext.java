package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteEntityParser;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteEntityParserContext;
import com.th3l4b.srm.runtime.DefaultPerEntityContext;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class DefaultAndroidSQLiteEntityParserContext extends
		DefaultPerEntityContext<IAndroidSQLiteEntityParser<?>> implements
		IAndroidSQLiteEntityParserContext {

	@SuppressWarnings("unchecked")
	@Override
	public <R extends IRuntimeEntity<R>> IAndroidSQLiteEntityParser<R> getEntityParser(
			Class<R> clazz) throws Exception {
		return (IAndroidSQLiteEntityParser<R>) get(clazz);
	}

}
