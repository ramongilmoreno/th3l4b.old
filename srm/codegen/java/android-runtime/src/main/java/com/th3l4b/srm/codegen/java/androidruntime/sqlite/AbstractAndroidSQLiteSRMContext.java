package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import android.database.sqlite.SQLiteDatabase;

import com.th3l4b.srm.codegen.java.androidruntime.sqlite.types.AndroidSQLiteRuntimeTypesBasicSet;
import com.th3l4b.srm.codegen.java.basicruntime.update.AbstractUpdateToolSRMContext;
import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolFinder;
import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolUpdater;

public abstract class AbstractAndroidSQLiteSRMContext<FINDER>
		extends
		AbstractUpdateToolSRMContext<FINDER, IAndroidSQLiteIdentifierParser, IAndroidSQLiteStatusParser, IAndroidSQLiteEntityParserContext, IAndroidSQLiteRuntimeTypesContext> {

	protected abstract SQLiteDatabase getDatabase() throws Exception;

	@Override
	protected IAndroidSQLiteRuntimeTypesContext createTypes() throws Exception {
		return new AndroidSQLiteRuntimeTypesBasicSet();
	}

	@Override
	protected IAndroidSQLiteIdentifierParser createIdentifierParser()
			throws Exception {
		return new DefaultAndroidSQLiteIndentifierParser();
	}

	@Override
	protected IAndroidSQLiteStatusParser createStatusParser() throws Exception {
		return new DefaultAndroidSQLiteStatusParser();
	}

	@Override
	protected IUpdateToolFinder createUpdateToolFinder() throws Exception {
		return new AbstractAndroidSQLiteUpdateToolFinder() {
			@Override
			protected IAndroidSQLiteEntityParserContext getParsers()
					throws Exception {
				return AbstractAndroidSQLiteSRMContext.this.getParsers();
			}

			@Override
			protected SQLiteDatabase getDatabase() throws Exception {
				return AbstractAndroidSQLiteSRMContext.this.getDatabase();
			}
		};
	}

	@Override
	protected IUpdateToolUpdater createUpdateToolUpdater() throws Exception {
		return new AbstractAndroidSQLiteUpdateToolUpdater() {
			@Override
			protected IAndroidSQLiteEntityParserContext getParsers()
					throws Exception {
				return AbstractAndroidSQLiteSRMContext.this.getParsers();
			}

			@Override
			protected SQLiteDatabase getDatabase() throws Exception {
				return AbstractAndroidSQLiteSRMContext.this.getDatabase();
			}
		};
	}

}
