package com.th3l4b.android.srm.runtime.junit;

import com.th3l4b.android.srm.runtime.sqlite.DefaultAndroidSQLiteEntityParserContext;
import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteIdentifierParser;
import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteStatusParser;

public class SampleAndroidSQLiteEntityParserContext extends
		DefaultAndroidSQLiteEntityParserContext {
	public SampleAndroidSQLiteEntityParserContext(
			IAndroidSQLiteIdentifierParser idsParser,
			IAndroidSQLiteStatusParser statusParser) throws Exception {
		put(ISampleEntity.class, new SampleAndroidSQLiteEntityParser(idsParser,
				statusParser));
	}
}
