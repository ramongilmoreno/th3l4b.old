package com.th3l4b.srm.codegen.java.mongoruntime.junit;

import com.mongodb.DB;
import com.th3l4b.srm.codegen.java.mongoruntime.AbstractMongoSRMContext;
import com.th3l4b.srm.codegen.java.mongoruntime.IMongoEntityParserContext;
import com.th3l4b.srm.codegen.java.mongoruntime.IMongoIdentifierParser;
import com.th3l4b.srm.codegen.java.mongoruntime.IMongoRuntimeTypesContext;
import com.th3l4b.srm.codegen.java.mongoruntime.IMongoStatusParser;

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
