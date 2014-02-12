package com.th3l4b.android.srm.codegen.sqlite;

import com.th3l4b.android.srm.codegen.AndroidCodeGeneratorContext;
import com.th3l4b.srm.codegen.database.SQLNames;

public class AndroidSQLiteCodeGeneratorContext extends
		AndroidCodeGeneratorContext {

	private AndroidSQLiteNames _SQLiteNames = new AndroidSQLiteNames();
	private SQLNames _SQLNames = new SQLNames();

	public AndroidSQLiteCodeGeneratorContext() throws Exception {
		super();
	}

	public AndroidSQLiteNames getSQLiteNames() {
		return _SQLiteNames;
	}

	public void setSQLiteNames(AndroidSQLiteNames sQLiteNames) {
		_SQLiteNames = sQLiteNames;
	}
	
	public SQLNames getSQLNames() {
		return _SQLNames;
	}
	
	public void setSQLNames(SQLNames sQLNames) {
		_SQLNames = sQLNames;
	}

	public void copyTo(AndroidSQLiteCodeGeneratorContext to) throws Exception {
		super.copyTo(to);
		to.setSQLiteNames(getSQLiteNames());
	}

}
