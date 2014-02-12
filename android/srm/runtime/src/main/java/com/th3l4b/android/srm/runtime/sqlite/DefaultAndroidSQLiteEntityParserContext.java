package com.th3l4b.android.srm.runtime.sqlite;

import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteEntityParser;
import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteEntityParserContext;
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
