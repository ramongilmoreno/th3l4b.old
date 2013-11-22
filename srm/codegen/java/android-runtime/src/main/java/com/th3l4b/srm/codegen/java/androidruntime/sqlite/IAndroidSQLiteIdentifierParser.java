package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IIdentifierParser;

public interface IAndroidSQLiteIdentifierParser extends
		IIdentifierParser<Cursor, ContentValues, Integer, Void> {
	String toString(IIdentifier identifier) throws Exception;
}
