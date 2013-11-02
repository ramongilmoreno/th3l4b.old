package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class AbstractSQLiteOpenHelper extends SQLiteOpenHelper {

	public AbstractSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}


}
