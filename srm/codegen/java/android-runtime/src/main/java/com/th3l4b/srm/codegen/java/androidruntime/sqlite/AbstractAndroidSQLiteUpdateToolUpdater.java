package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.th3l4b.srm.codegen.java.basicruntime.update.AbstractUpdateToolUpdater;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractAndroidSQLiteUpdateToolUpdater extends
		AbstractUpdateToolUpdater {

	protected abstract SQLiteDatabase getDatabase() throws Exception;

	protected abstract IAndroidSQLiteEntityParserContext getParsers()
			throws Exception;

	protected abstract IAndroidSQLiteIdentifierParser getIdentifierParser()
			throws Exception;

	@Override
	protected <T extends IRuntimeEntity<T>> void updateEntity(T entity,
			T original, IModelUtils utils) throws Exception {
		IAndroidSQLiteEntityParser<T> parser = getParsers().getEntityParser(
				entity.clazz());
		ContentValues values = new ContentValues();
		parser.set(entity, null, values);
		getDatabase().update(
				parser.table(),
				values,
				"" + parser.idColumn() + " = ?",
				new String[] { getIdentifierParser().toString(
						entity.coordinates().getIdentifier()) });
	}
}
