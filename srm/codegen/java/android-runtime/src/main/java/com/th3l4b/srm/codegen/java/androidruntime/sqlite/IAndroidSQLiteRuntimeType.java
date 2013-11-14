package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import com.th3l4b.srm.runtime.IBaseParser;

import android.content.ContentValues;
import android.database.Cursor;

public interface IAndroidSQLiteRuntimeType<T> extends
		IBaseParser<T, Cursor, ContentValues, Integer, Void> {
	Class<T> clazz();

	<T2> T2 parse(Integer index, Cursor result, Class<T2> clazz)
			throws Exception;

	<T2> T2 set(T2 value, Void arg, ContentValues values, Class<T2> clazz)
			throws Exception;
}