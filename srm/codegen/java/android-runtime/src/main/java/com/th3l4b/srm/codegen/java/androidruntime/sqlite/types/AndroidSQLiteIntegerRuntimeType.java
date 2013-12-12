package com.th3l4b.srm.codegen.java.androidruntime.sqlite.types;

import android.content.ContentValues;
import android.database.Cursor;

public class AndroidSQLiteIntegerRuntimeType extends
		AbstractAndroidSQLiteType<Long> {

	@Override
	public Long parseNotNull(int i, Cursor result) throws Exception {
		return result.getLong(i);
	}

	@Override
	public void setNotNull(Long value, String arg, ContentValues statement)
			throws Exception {
		statement.put(arg, value);
	}

}
