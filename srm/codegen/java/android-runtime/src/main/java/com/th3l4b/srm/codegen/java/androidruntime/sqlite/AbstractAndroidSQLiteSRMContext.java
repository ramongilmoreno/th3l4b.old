package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import android.database.sqlite.SQLiteDatabase;

import com.th3l4b.srm.codegen.java.androidruntime.sqlite.types.AndroidSQLiteRuntimeTypesBasicSet;
import com.th3l4b.srm.codegen.java.basicruntime.update.AbstractUpdateToolSRMContext;

public abstract class AbstractAndroidSQLiteSRMContext<FINDER>
		extends
		AbstractUpdateToolSRMContext<FINDER, IAndroidSQLiteIdentifierParser, IAndroidSQLiteStatusParser, IAndroidSQLiteEntityParserContext, IAndroidSQLiteRuntimeTypesContext> {

	protected abstract SQLiteDatabase getDatabase() throws Exception;
	
	@Override
	protected IAndroidSQLiteRuntimeTypesContext createTypes() throws Exception {
		return new AndroidSQLiteRuntimeTypesBasicSet();
	}

}
