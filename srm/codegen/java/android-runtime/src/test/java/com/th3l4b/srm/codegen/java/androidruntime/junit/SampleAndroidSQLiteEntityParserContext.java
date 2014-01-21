package com.th3l4b.srm.codegen.java.androidruntime.junit;

import com.th3l4b.srm.codegen.java.androidruntime.sqlite.DefaultAndroidSQLiteEntityParserContext;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteIdentifierParser;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteStatusParser;

public class SampleAndroidSQLiteEntityParserContext extends
		DefaultAndroidSQLiteEntityParserContext {
	public SampleAndroidSQLiteEntityParserContext(
			IAndroidSQLiteIdentifierParser idsParser,
			IAndroidSQLiteStatusParser statusParser) throws Exception {
		put(ISampleEntity.class, new SampleAndroidSQLiteEntityParser(idsParser,
				statusParser));
	}
}
