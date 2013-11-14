package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.srm.runtime.IDatabaseParser;

public interface IAndroidSQLiteEntityParser<R> extends
		IDatabaseParser<R, Cursor, ContentValues, Integer, Void> {
}
