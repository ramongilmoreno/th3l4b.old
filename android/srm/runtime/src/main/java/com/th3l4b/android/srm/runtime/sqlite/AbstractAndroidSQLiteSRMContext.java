package com.th3l4b.android.srm.runtime.sqlite;

import android.database.sqlite.SQLiteDatabase;

import com.th3l4b.android.srm.runtime.sqlite.types.AndroidSQLiteRuntimeTypesBasicSet;
import com.th3l4b.srm.codegen.java.basic.runtime.update.AbstractUpdateToolSRMContext;
import com.th3l4b.srm.codegen.java.basic.runtime.update.IUpdateToolFinder;
import com.th3l4b.srm.codegen.java.basic.runtime.update.IUpdateToolUpdater;
import com.th3l4b.srm.runtime.IFinder;

public abstract class AbstractAndroidSQLiteSRMContext<FINDER extends IFinder>
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
