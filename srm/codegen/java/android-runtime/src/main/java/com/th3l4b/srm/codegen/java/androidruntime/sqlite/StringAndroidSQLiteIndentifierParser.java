package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.srm.codegen.java.basicruntime.StringIdentifier;
import com.th3l4b.srm.runtime.IIdentifier;

public class StringAndroidSQLiteIndentifierParser implements
		IAndroidSQLiteIdentifierParser {

	@Override
	public IIdentifier parse(Integer arg, Cursor result) throws Exception {
		return new StringIdentifier(result.getString(arg.intValue()));
	}

	@Override
	public void set(IIdentifier value, String arg, ContentValues statement)
			throws Exception {
		statement.put(arg, toString(value));
	}

	@Override
	public String toString(IIdentifier identifier) throws Exception {
		return ((StringIdentifier) identifier).getValue();
	}

}
