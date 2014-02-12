package com.th3l4b.android.srm.runtime.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.srm.runtime.IEntityStatusParser;

public interface IAndroidSQLiteStatusParser extends
		IEntityStatusParser<Cursor, ContentValues, Integer, String> {
}
