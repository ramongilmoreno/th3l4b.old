package com.th3l4b.srm.codegen.java.mongoruntime.junit;

import com.th3l4b.srm.codegen.java.mongoruntime.DefaultMongoEntityParserContext;
import com.th3l4b.srm.codegen.java.mongoruntime.IMongoIdentifierParser;
import com.th3l4b.srm.codegen.java.mongoruntime.IMongoRuntimeTypesContext;
import com.th3l4b.srm.codegen.java.mongoruntime.IMongoStatusParser;

public class SampleMongoParsers extends DefaultMongoEntityParserContext {
	public SampleMongoParsers(IMongoIdentifierParser ids,
			IMongoStatusParser status, IMongoRuntimeTypesContext types)
			throws Exception {
		put(ISampleEntity.class, new SampleEntityParser(ids, status, types));
	}
}
