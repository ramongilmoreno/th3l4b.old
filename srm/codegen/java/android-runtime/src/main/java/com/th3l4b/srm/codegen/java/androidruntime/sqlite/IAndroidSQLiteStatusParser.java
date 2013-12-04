package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.srm.runtime.IEntityStatusParser;

public interface IAndroidSQLiteStatusParser extends
		IEntityStatusParser<Cursor, ContentValues, Integer, String> {
}
