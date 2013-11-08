package com.th3l4b.srm.codegen.java.android.sqlite.junit;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TestSQLiteOpenHelper extends SQLiteOpenHelper {

	public TestSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);

	}

	public TestSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	public void onCreate(SQLiteDatabase arg0) {
	}

	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

}
