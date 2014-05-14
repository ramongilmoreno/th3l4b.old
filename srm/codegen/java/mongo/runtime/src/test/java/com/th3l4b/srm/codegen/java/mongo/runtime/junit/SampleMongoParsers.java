package com.th3l4b.srm.codegen.java.mongo.runtime.junit;

import com.th3l4b.srm.codegen.java.mongo.runtime.DefaultMongoEntityParserContext;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoIdentifierParser;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoRuntimeTypesContext;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoStatusParser;

public class SampleMongoParsers extends DefaultMongoEntityParserContext {
	public SampleMongoParsers(IMongoIdentifierParser ids,
			IMongoStatusParser status, IMongoRuntimeTypesContext types)
			throws Exception {
		put(ISampleEntity.class, new SampleEntityParser(ids, status, types));
	}
}
