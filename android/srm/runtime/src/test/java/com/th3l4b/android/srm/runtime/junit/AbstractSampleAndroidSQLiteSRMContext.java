package com.th3l4b.android.srm.runtime.junit;

import android.database.sqlite.SQLiteDatabase;

import com.th3l4b.android.srm.runtime.sqlite.AbstractAndroidSQLiteSRMContext;
import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteEntityParserContext;
import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteIdentifierParser;
import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteRuntimeTypesContext;
import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteStatusParser;
import com.th3l4b.srm.codegen.java.basic.runtime.AbstractModelUtils;
import com.th3l4b.srm.runtime.IModelUtils;

public abstract class AbstractSampleAndroidSQLiteSRMContext extends
		AbstractAndroidSQLiteSRMContext<ISampleFinder> {

	@Override
	protected IAndroidSQLiteEntityParserContext createParsers()
			throws Exception {
		return new SampleAndroidSQLiteEntityParserContext(
				getIdentifierParser(), getStatusParser());
	}

	@Override
	protected IModelUtils createUtils() throws Exception {
		return new AbstractModelUtils() {
		};
	}

	@Override
	protected ISampleFinder createFinder() throws Exception {
		return new AbstractSampleAndroidSQLiteFinder() {
			@Override
			protected SQLiteDatabase getDatabase() throws Exception {
				return AbstractSampleAndroidSQLiteSRMContext.this.getDatabase();
			}

			@Override
			protected IAndroidSQLiteIdentifierParser getIdentifierParser()
					throws Exception {
				return AbstractSampleAndroidSQLiteSRMContext.this.getIdentifierParser();
			}

			@Override
			protected IAndroidSQLiteStatusParser getStatusParser()
					throws Exception {
				return AbstractSampleAndroidSQLiteSRMContext.this.getStatusParser();
			}

			@Override
			protected IAndroidSQLiteRuntimeTypesContext getTypes()
					throws Exception {
				return AbstractSampleAndroidSQLiteSRMContext.this.getTypes();
			}

			@Override
			protected IAndroidSQLiteEntityParserContext getParsers()
					throws Exception {
				return AbstractSampleAndroidSQLiteSRMContext.this.getParsers();
			}
		};
	}

}
