package com.th3l4b.srm.codegen.java.android.sqlite;

import android.database.sqlite.SQLiteOpenHelper;

import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.java.android.AndroidNames;

public class AndroidSQLiteNames extends AndroidNames {
	public String packageForSQLite(
			AndroidSQLiteCodeGeneratorContext context) {
		return super.packageForAndroid(context) + ".sqlite";
	}

	public String helper(INormalizedModel model,
			AndroidSQLiteCodeGeneratorContext context) throws Exception {
		return valueOrProperty(javaIdentifier(model.getName())
				+ SQLiteOpenHelper.class.getSimpleName(), model);
	}
}
