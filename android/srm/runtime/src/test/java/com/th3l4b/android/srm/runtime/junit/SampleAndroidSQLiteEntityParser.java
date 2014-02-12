package com.th3l4b.android.srm.runtime.junit;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.android.srm.runtime.sqlite.AbstractAndroidSQLiteEntityParser;
import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteIdentifierParser;
import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteRuntimeType;
import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteStatusParser;

public class SampleAndroidSQLiteEntityParser extends AbstractAndroidSQLiteEntityParser<ISampleEntity>{

	public SampleAndroidSQLiteEntityParser(
			IAndroidSQLiteIdentifierParser idsParser,
			IAndroidSQLiteStatusParser statusParser) {
		super(idsParser, statusParser);
	}

	@Override
	public ISampleEntity create() {
		return null;
	}

	@Override
	public String table() throws Exception {
		return "TABLE";
	}

	@Override
	public String idColumn() throws Exception {
		return "ID";
	}

	@Override
	public String statusColumn() throws Exception {
		return "STATUS";
	}
	
	private String [] COLUMNS = {
		"A", "B"	
	};

	@Override
	public String[] fieldsColumns() throws Exception {
		return COLUMNS;
	}
	
	IAndroidSQLiteRuntimeType<String> _parser_Field1;

	@Override
	protected void parseRest(ISampleEntity entity, int index, Cursor result)
			throws Exception {
		entity.setField1(_parser_Field1.parse(index++, result));
		entity.setRelation(getIdsParser().parse(index++, result));
	}

	@Override
	public void setRest(ISampleEntity entity, Void arg, ContentValues values)
			throws Exception {
	}

}
