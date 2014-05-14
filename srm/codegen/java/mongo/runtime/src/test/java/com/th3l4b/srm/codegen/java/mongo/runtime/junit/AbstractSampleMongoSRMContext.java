package com.th3l4b.srm.codegen.java.mongo.runtime.junit;

import com.mongodb.DB;
import com.th3l4b.srm.codegen.java.mongo.runtime.AbstractMongoSRMContext;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoEntityParserContext;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoIdentifierParser;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoRuntimeTypesContext;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoStatusParser;

public abstract class AbstractSampleMongoSRMContext extends
		AbstractMongoSRMContext<ISampleFinder> {

	@Override
	protected IMongoEntityParserContext createParsers() throws Exception {
		return new SampleMongoParsers(getIdentifierParser(), getStatusParser(),
				getTypes());
	}

	@Override
	protected ISampleFinder createFinder() throws Exception {
		return new AbstractSampleMongoFinder() {

			@Override
			protected IMongoIdentifierParser getIdentifierParser()
					throws Exception {
				return AbstractSampleMongoSRMContext.this.getIdentifierParser();
			}

			@Override
			protected IMongoStatusParser getStatusParser() throws Exception {
				return AbstractSampleMongoSRMContext.this.getStatusParser();
			}

			@Override
			protected IMongoRuntimeTypesContext getTypes() throws Exception {
				return AbstractSampleMongoSRMContext.this.getTypes();
			}

			@Override
			protected IMongoEntityParserContext getParsers() throws Exception {
				return AbstractSampleMongoSRMContext.this.getParsers();
			}

			@Override
			protected DB getDB() throws Exception {
				return AbstractSampleMongoSRMContext.this.getDB();
			}
		};
	}

}
