package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import java.util.HashMap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.th3l4b.srm.codegen.java.basicruntime.update.AbstractUpdateToolFinder;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractAndroidSQLiteUpdateToolFinder extends
		AbstractUpdateToolFinder {

	protected abstract SQLiteDatabase getDatabase() throws Exception;

	protected abstract IAndroidSQLiteEntityParserContext getParsers()
			throws Exception;

	@Override
	protected <T extends IRuntimeEntity<T>> void findEntity(
			IRuntimeEntity<T> entity,
			HashMap<IIdentifier, IRuntimeEntity<?>> r, IModelUtils utils)
			throws Exception {
		IAndroidSQLiteEntityParser<T> parser = getParsers().getEntityParser(
				entity.clazz());
		Cursor result = getDatabase().query(parser.table(),
				parser.allColumns(), "" + parser.idColumn() + " = ?",
				new String[] { entity.coordinates().getIdentifier().getKey() },
				null, null, null);
		result.moveToFirst();
		try {
			T t = parser.parse(0, result);
			r.put(t.coordinates().getIdentifier(), t);
		} finally {
			result.close();
		}
	}
}
