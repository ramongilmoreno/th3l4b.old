package com.th3l4b.srm.codegen.java.mongo.runtime;

import com.mongodb.DB;
import com.th3l4b.srm.codegen.java.basic.runtime.update.AbstractUpdateToolSRMContext;
import com.th3l4b.srm.codegen.java.basic.runtime.update.IUpdateToolFinder;
import com.th3l4b.srm.codegen.java.basic.runtime.update.IUpdateToolUpdater;
import com.th3l4b.srm.codegen.java.mongo.runtime.types.MongoRuntimeTypesBasicSet;
import com.th3l4b.srm.runtime.IFinder;

public abstract class AbstractMongoSRMContext<FINDER extends IFinder>
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
