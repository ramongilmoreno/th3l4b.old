package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractAndroidSQLiteEntityParser<R extends IRuntimeEntity<R>>
		implements IAndroidSQLiteEntityParser<R> {

	protected IAndroidSQLiteIdentifierParser _idsParser;
	protected IAndroidSQLiteStatusParser _statusParser;

	public AbstractAndroidSQLiteEntityParser(
			IAndroidSQLiteIdentifierParser idsParser,
			IAndroidSQLiteStatusParser statusParser) {
		_idsParser = idsParser;
		_statusParser = statusParser;
	}

	public IAndroidSQLiteIdentifierParser getIdsParser() {
		return _idsParser;
	}

	public void setIdsParser(IAndroidSQLiteIdentifierParser idsParser) {
		_idsParser = idsParser;
	}

	public IAndroidSQLiteStatusParser getStatusParser() {
		return _statusParser;
	}

	public void setStatusParser(IAndroidSQLiteStatusParser statusParser) {
		_statusParser = statusParser;
	}

	@Override
	public R parse(Integer idx, Cursor result) throws Exception {
		int index = idx;
		R entity = create();
		ICoordinates coordinates = entity.coordinates();
		coordinates.setIdentifier(getIdsParser().parse(index++, result));
		coordinates.setStatus(getStatusParser().parse(index++, result));
		parseRest(entity, index, result);
		return entity;
	}

	protected abstract void parseRest(R entity, int index, Cursor result)
			throws Exception;

	@Override
	public void set(R entity, Void arg, ContentValues values) throws Exception {
		ICoordinates coordinates = entity.coordinates();
		getIdsParser().set(coordinates.getIdentifier(), arg, values);
		getStatusParser().set(coordinates.getStatus(), arg, values);
		setRest(entity, arg, values);
	}

	public abstract void setRest(R entity, Void arg, ContentValues values)
			throws Exception;

	String[] _allColumns;

	@Override
	public String[] allColumns() throws Exception {
		if (_allColumns == null) {
			String[] fieldsColumns = fieldsColumns();
			String[] r = new String[fieldsColumns.length];
			r[0] = idColumn();
			r[1] = statusColumn();
			System.arraycopy(fieldsColumns, 0, r, 2, fieldsColumns.length);
			_allColumns = r;
		}
		return _allColumns;
	}
}
