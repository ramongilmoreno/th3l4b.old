package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.srm.codegen.java.basicruntime.DefaultIdentifier;
import com.th3l4b.srm.runtime.IIdentifier;

public class DefaultAndroidSQLiteIndentifierParser implements
		IAndroidSQLiteIdentifierParser {

	@Override
	public IIdentifier parse(Integer arg, Cursor result) throws Exception {
		return new DefaultIdentifier(result.getString(arg.intValue()));
	}

	@Override
	public void set(IIdentifier value, String arg, ContentValues statement)
			throws Exception {
		statement.put(arg, value.getKey());
	}
}
