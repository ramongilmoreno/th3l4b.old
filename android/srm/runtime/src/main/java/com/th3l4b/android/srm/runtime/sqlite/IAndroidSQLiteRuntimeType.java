package com.th3l4b.android.srm.runtime.sqlite;

import com.th3l4b.srm.runtime.IBaseParser;

import android.content.ContentValues;
import android.database.Cursor;

public interface IAndroidSQLiteRuntimeType<T> extends
		IBaseParser<T, Cursor, ContentValues, Integer, String> {
}