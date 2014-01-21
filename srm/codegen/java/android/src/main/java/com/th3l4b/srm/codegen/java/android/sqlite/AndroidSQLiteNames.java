package com.th3l4b.srm.codegen.java.android.sqlite;

import android.database.sqlite.SQLiteOpenHelper;

import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.java.android.AndroidNames;

public class AndroidSQLiteNames extends AndroidNames {

	public String packageForSQLite(AndroidSQLiteCodeGeneratorContext context) {
		return super.packageForAndroid(context) + ".sqlite";
	}

	public String helper(INormalizedModel model,
			AndroidSQLiteCodeGeneratorContext context) throws Exception {
		return valueOrProperty(javaIdentifier(model.getName())
				+ SQLiteOpenHelper.class.getSimpleName(), model);
	}
	
	public String finder(INormalizedModel model,
			AndroidSQLiteCodeGeneratorContext context) throws Exception {
		return valueOrProperty("Abstract" + javaIdentifier(model.getName())
				+ "AndroidSQLiteFinder", model);
	}


	public String packageForSQLiteParsers(
			AndroidSQLiteCodeGeneratorContext context) {
		return packageForSQLite(context) + ".parsers";
	}

	public String fqnSQLiteParsers(String clazz,
			AndroidSQLiteCodeGeneratorContext context) {
		return packageForSQLiteParsers(context) + "." + clazz;
	}

	public String parserAndroidSQLite(INormalizedEntity entity)
			throws Exception {
		return valueOrProperty(javaIdentifier(entity.getName()) + "Parser",
				entity);
	}

}
