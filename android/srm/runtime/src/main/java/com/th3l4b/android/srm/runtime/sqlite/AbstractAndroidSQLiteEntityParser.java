package com.th3l4b.android.srm.runtime.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.srm.codegen.java.basic.runtime.DefaultIdentifier;
import com.th3l4b.srm.runtime.AbstractEntityParser;
import com.th3l4b.srm.runtime.DatabaseUtils;
import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IDatabaseConstants;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractAndroidSQLiteEntityParser<R extends IRuntimeEntity<R>>
		extends
		AbstractEntityParser<IAndroidSQLiteIdentifierParser, IAndroidSQLiteStatusParser>
		implements IAndroidSQLiteEntityParser<R> {

	public AbstractAndroidSQLiteEntityParser(
			IAndroidSQLiteIdentifierParser idsParser,
			IAndroidSQLiteStatusParser statusParser) {
		super(idsParser, statusParser);
	}

	@Override
	public R parse(Integer idx, Cursor result) throws Exception {
		int index = idx;
		R entity = create();
		ICoordinates coordinates = entity.coordinates();
		Class<R> clazz = entity.clazz();
		coordinates.setIdentifier(new DefaultIdentifier(clazz.getName(),
				getIdsParser().parse(index++, result).getKey()));
		coordinates.setStatus(getStatusParser().parse(index++, result));
		parseRest(entity, index, result);
		return entity;
	}

	protected abstract void parseRest(R entity, int index, Cursor result)
			throws Exception;

	@Override
	public void set(R entity, Void arg, ContentValues values) throws Exception {
		ICoordinates coordinates = entity.coordinates();
		getIdsParser().set(coordinates.getIdentifier(),
				DatabaseUtils.column(IDatabaseConstants.ID, true), values);
		getStatusParser().set(coordinates.getStatus(),
				DatabaseUtils.column(IDatabaseConstants.STATUS, true), values);
		setRest(entity, arg, values);
	}

	public abstract void setRest(R entity, Void arg, ContentValues values)
			throws Exception;

	@Override
	public boolean hasValue(Integer arg, Cursor result) throws Exception {
		return true;
	}
}
