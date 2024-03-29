package com.th3l4b.android.srm.runtime.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.srm.runtime.IDatabaseParser;

public interface IAndroidSQLiteEntityParser<R> extends
		IDatabaseParser<R, Cursor, ContentValues, Integer, Void> {
	String[] allColumns () throws Exception;
}
