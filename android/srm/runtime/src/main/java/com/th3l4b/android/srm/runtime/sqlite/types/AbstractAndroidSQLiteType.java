package com.th3l4b.android.srm.runtime.sqlite.types;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteRuntimeType;

public abstract class AbstractAndroidSQLiteType<T> implements
		IAndroidSQLiteRuntimeType<T> {

	@Override
	public boolean hasValue(Integer arg, Cursor result) throws Exception {
		return true;
	}

	public abstract T parseNotNull(int i, Cursor result) throws Exception;

	@Override
	public T parse(Integer arg, Cursor result) throws Exception {
		int i = arg.intValue();
		return result.isNull(i) ? null : parseNotNull(i, result);
	}

	public abstract void setNotNull(T value, String arg, ContentValues statement)
			throws Exception;

	@Override
	public void set(T value, String arg, ContentValues statement)
			throws Exception {
		if (value != null) {
			setNotNull(value, arg, statement);
		} else {
			statement.putNull(arg);
		}
	}
}
