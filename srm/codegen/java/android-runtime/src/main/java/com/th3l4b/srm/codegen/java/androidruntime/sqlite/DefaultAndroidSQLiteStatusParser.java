package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.srm.runtime.EntityStatus;

public class DefaultAndroidSQLiteStatusParser implements
		IAndroidSQLiteStatusParser {

	@Override
	public EntityStatus parse(Integer arg, Cursor result) throws Exception {
		return EntityStatus.fromInitial(result.getString(arg.intValue()),
				EntityStatus.Unknown);
	}

	@Override
	public void set(EntityStatus value, String arg, ContentValues statement)
			throws Exception {
		statement.put(arg, AndroidSQLiteUtils.fromEntityStatus(value));
	}

}
