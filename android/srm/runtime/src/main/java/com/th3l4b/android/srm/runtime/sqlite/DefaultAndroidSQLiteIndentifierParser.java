package com.th3l4b.android.srm.runtime.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.codegen.java.basicruntime.DefaultIdentifier;
import com.th3l4b.srm.runtime.IIdentifier;

public class DefaultAndroidSQLiteIndentifierParser implements
		IAndroidSQLiteIdentifierParser {

	@Override
	public boolean hasValue(Integer arg, Cursor result) throws Exception {
		return true;
	}

	@Override
	public IIdentifier parse(Integer arg, Cursor result) throws Exception {
		String id = result.getString(arg.intValue());
		if (TextUtils.empty(id)) {
			return null;
		} else {
			return new DefaultIdentifier(id);
		}
	}

	@Override
	public void set(IIdentifier value, String arg, ContentValues statement)
			throws Exception {
		statement.put(arg, AndroidSQLiteUtils.fromIdentifier(value));
	}
}
