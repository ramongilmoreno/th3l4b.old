package com.th3l4b.srm.codegen.java.androidruntime.sqlite.types;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteRuntimeType;

public class AndroidSQLiteStringRuntimeType implements IAndroidSQLiteRuntimeType<String> {

	@Override
	public String parse(Integer arg, Cursor result) throws Exception {
		return result.getString(arg.intValue());
	}

	@Override
	public void set(String value, String arg, ContentValues statement)
			throws Exception {
		statement.put(arg, value);
	}
	

}
