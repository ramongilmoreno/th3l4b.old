package com.th3l4b.srm.codegen.java.mongoruntime;

import com.mongodb.DB;
import com.th3l4b.srm.codegen.java.basicruntime.update.AbstractUpdateToolSRMContext;
import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolFinder;
import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolUpdater;
import com.th3l4b.srm.codegen.java.mongoruntime.types.MongoRuntimeTypesBasicSet;

public abstract class AbstractMongoSRMContext<FINDER>
		extends
		AbstractUpdateToolSRMContext<FINDER, IMongoIdentifierParser, IMongoStatusParser, IMongoEntityParserContext, IMongoRuntimeTypesContext> {

	protected abstract DB getDB() throws Exception;

	@Override
	protected IMongoRuntimeTypesContext createTypes() throws Exception {
		return new MongoRuntimeTypesBasicSet();
	}

	@Override
	protected IMongoIdentifierParser createIdentifierParser() throws Exception {
		return new DefaultMongoIdentifierParser();
	}

	@Override
	protected IMongoStatusParser createStatusParser() throws Exception {
		return new DefaultMongoStatusParser();
	}

	@Override
	protected IUpdateToolFinder createUpdateToolFinder() throws Exception {
		return new AbstractMongoUpdateToolFinder() {

			@Override
			protected DB getDB() throws Exception {
				return AbstractMongoSRMContext.this.getDB();
			}

			@Override
			protected IMongoEntityParserContext getParsers() throws Exception {
				return AbstractMongoSRMContext.this.getParsers();
			}

			@Override
			protected IMongoIdentifierParser getIdentifierParser()
					throws Exception {
				return AbstractMongoSRMContext.this.getIdentifierParser();
			}
		};
	}

	@Override
	protected IUpdateToolUpdater createUpdateToolUpdater() throws Exception {
		return new AbstractMongoUpdateToolUpdater() {
			@Override
			protected DB getDB() throws Exception {
				return AbstractMongoSRMContext.this.getDB();
			}

			@Override
			protected IMongoEntityParserContext getParsers() throws Exception {
				return AbstractMongoSRMContext.this.getParsers();
			}

			@Override
			protected IMongoIdentifierParser getIdentifierParser()
					throws Exception {
				return AbstractMongoSRMContext.this.getIdentifierParser();
			}
		};
	}
}
