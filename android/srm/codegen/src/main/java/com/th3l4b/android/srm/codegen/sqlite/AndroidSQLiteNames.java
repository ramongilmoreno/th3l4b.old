package com.th3l4b.android.srm.codegen.sqlite;

import android.database.sqlite.SQLiteOpenHelper;

import com.th3l4b.android.srm.codegen.AndroidNames;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.names.BaseNames;

public class AndroidSQLiteNames {

	private BaseNames _baseNames;
	private AndroidNames _androidNames;

	public AndroidSQLiteNames(BaseNames baseNames, AndroidNames androidNames) {
		_baseNames = baseNames;
		_androidNames = androidNames;
	}

	public String packageForSQLite(AndroidSQLiteCodeGeneratorContext context) {
		return _androidNames.packageForAndroid(context) + ".sqlite";
	}

	public String helper(INormalizedModel model,
			AndroidSQLiteCodeGeneratorContext context) throws Exception {
		return _baseNames.name(model) + SQLiteOpenHelper.class.getSimpleName();
	}

	public String finder(INormalizedModel model,
			AndroidSQLiteCodeGeneratorContext context) throws Exception {
		return "Abstract" + _baseNames.name(model) + "AndroidSQLiteFinder";
	}

	public String context(INormalizedModel model,
			AndroidSQLiteCodeGeneratorContext context) throws Exception {
		return "Abstract" + _baseNames.name(model) + "AndroidSQLiteContext";
	}

	public String parserContext(INormalizedModel model,
			AndroidSQLiteCodeGeneratorContext context) throws Exception {
		return "Abstract" + _baseNames.name(model)
				+ "AndroidSQLiteParserContext";
	}

	public String packageForSQLiteParsers(
			AndroidSQLiteCodeGeneratorContext context) {
		return packageForSQLite(context) + ".parsers";
	}

	public String fqnSQLite(String clazz,
			AndroidSQLiteCodeGeneratorContext context) {
		return packageForSQLite(context) + "." + clazz;
	}

	public String fqnSQLiteParsers(String clazz,
			AndroidSQLiteCodeGeneratorContext context) {
		return packageForSQLiteParsers(context) + "." + clazz;
	}

	public String parserAndroidSQLite(INormalizedEntity entity)
			throws Exception {
		return _baseNames.name(entity) + "Parser";
	}
}
