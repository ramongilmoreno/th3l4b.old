package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.th3l4b.srm.codegen.java.basicruntime.inmemory.Pair;
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractAndroidSQLiteFinder {

	/**
	 * Maps the relationships of a class with a column name
	 */
	protected Map<Pair, String> _map = new LinkedHashMap<Pair, String>();

	protected abstract SQLiteDatabase getDatabase() throws Exception;

	protected abstract IAndroidSQLiteIdentifierParser getIdentifierParser()
			throws Exception;

	protected abstract IAndroidSQLiteStatusParser getStatusParser()
			throws Exception;

	protected abstract IAndroidSQLiteRuntimeTypesContext getTypes()
			throws Exception;

	protected abstract IAndroidSQLiteEntityParserContext getParsers()
			throws Exception;

	public <R extends IRuntimeEntity<R>, S extends IRuntimeEntity<S>> Iterable<R> find(
			Class<R> resultClass, Class<S> sourceClass,
			IIdentifier identifier, String relationship)
			throws Exception {
		String column = _map.get(new Pair(sourceClass, relationship));
		return find(resultClass, identifier, column);
	}
	
	public <R extends IRuntimeEntity<R>> Iterable<R> find(Class<R> resultClass,
			IIdentifier sourceIdentifier, String relationshipColumnName)
			throws Exception {
		IAndroidSQLiteEntityParser<R> parser = getParsers().getEntityParser(
				resultClass);
		StringBuffer query = new StringBuffer("select ");
		AndroidSQLiteUtils.columnsAndFrom(parser, query);
		query.append(" where ");
		query.append(relationshipColumnName);
		query.append(" = ? and ");
		query.append(parser.statusColumn());
		query.append(" = ?");

		Cursor cursor = getDatabase().rawQuery(
				query.toString(),
				new String[] {
						AndroidSQLiteUtils.fromIdentifier(sourceIdentifier),
						AndroidSQLiteUtils
								.fromEntityStatus(EntityStatus.Persisted) });
		cursor.moveToFirst();
		LinkedHashSet<R> r = new LinkedHashSet<R>();
		while (!cursor.isAfterLast()) {
			R entity = parser.parse(1, cursor);
			r.add(entity);
		}
		cursor.close();
		return r;
	}

	public <R extends IRuntimeEntity<R>> R find(Class<R> clazz,
			IIdentifier identifier) throws Exception {
		IAndroidSQLiteEntityParser<R> parser = getParsers().getEntityParser(
				clazz);
		StringBuffer query = new StringBuffer("select ");
		AndroidSQLiteUtils.columnsAndFrom(parser, query);
		query.append(" where ");
		query.append(parser.idColumn());
		query.append(" = ?");

		Cursor cursor = getDatabase().rawQuery(query.toString(),
				new String[] { AndroidSQLiteUtils.fromIdentifier(identifier) });

		cursor.moveToFirst();
		R r = null;
		while (!cursor.isAfterLast()) {
			r = parser.parse(1, cursor);
		}
		cursor.close();
		return r;
	}

	public <R extends IRuntimeEntity<R>> Iterable<R> all(final Class<R> clazz)
			throws Exception {
		IAndroidSQLiteEntityParser<R> parser = getParsers().getEntityParser(
				clazz);
		StringBuffer query = new StringBuffer("select ");
		AndroidSQLiteUtils.columnsAndFrom(parser, query);
		query.append(parser.table());
		query.append(" where ");
		query.append(parser.statusColumn());
		query.append(" = ?");

		Cursor cursor = getDatabase().rawQuery(
				query.toString(),
				new String[] { AndroidSQLiteUtils
						.fromEntityStatus(EntityStatus.Persisted) });
		cursor.moveToFirst();
		LinkedHashSet<R> r = new LinkedHashSet<R>();
		while (!cursor.isAfterLast()) {
			R entity = parser.parse(1, cursor);
			r.add(entity);
		}
		cursor.close();
		return r;
	}
}
