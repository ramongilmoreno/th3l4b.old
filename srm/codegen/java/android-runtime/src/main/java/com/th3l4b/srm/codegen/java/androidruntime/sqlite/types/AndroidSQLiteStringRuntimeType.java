package com.th3l4b.srm.codegen.java.androidruntime.sqlite.types;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.common.text.TextUtils;

public class AndroidSQLiteStringRuntimeType extends
		AbstractAndroidSQLiteType<String> {

	private Integer _limit;

	public AndroidSQLiteStringRuntimeType() {
		this(null);
	}

	public AndroidSQLiteStringRuntimeType(Integer limit) {
		_limit = limit;
	}

	protected String limit(String input) throws Exception {
		return _limit != null ? TextUtils.limit(input, _limit.intValue())
				: input;
	}

	@Override
	public String parseNotNull(int i, Cursor result) throws Exception {
		return result.getString(i);
	}

	@Override
	public void setNotNull(String value, String arg, ContentValues statement)
			throws Exception {
		statement.put(arg, value);
	}

}
