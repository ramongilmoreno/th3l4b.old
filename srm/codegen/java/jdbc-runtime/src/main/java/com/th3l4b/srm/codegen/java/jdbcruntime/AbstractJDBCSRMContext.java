package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.Connection;

import com.th3l4b.srm.codegen.java.basicruntime.update.AbstractUpdateToolSRMContext;
import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolFinder;
import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolUpdater;
import com.th3l4b.srm.codegen.java.jdbcruntime.types.JDBCRuntimeTypesBasicSet;
import com.th3l4b.srm.runtime.IModelUtils;

public abstract class AbstractJDBCSRMContext<FINDER>
		extends
		AbstractUpdateToolSRMContext<FINDER, IJDBCIdentifierParser, IJDBCStatusParser, IJDBCEntityParserContext, IJDBCRuntimeTypesContext> {

	protected abstract Connection getConnection() throws Exception;

	@Override
	protected IJDBCRuntimeTypesContext createTypes() throws Exception {
		return new JDBCRuntimeTypesBasicSet();
	}

	@Override
	protected IJDBCIdentifierParser createIdentifierParser() throws Exception {
		return new DefaultJDBCIdentifierParser();
	}

	@Override
	protected IJDBCStatusParser createStatusParser() throws Exception {
		return new DefaultJDBCStatusParser();
	}

	@Override
	protected IUpdateToolFinder createUpdateToolFinder() throws Exception {
		return new AbstractJDBCUpdateToolFinder() {
			@Override
			protected Connection getConnection() throws Exception {
				return AbstractJDBCSRMContext.this.getConnection();
			}

			@Override
			protected IJDBCEntityParserContext getParsers() throws Exception {
				return AbstractJDBCSRMContext.this.getParsers();
			}

			@Override
			protected IJDBCIdentifierParser getIdentifierParser()
					throws Exception {
				return AbstractJDBCSRMContext.this.getIdentifierParser();
			}
		};
	}

	@Override
	protected IUpdateToolUpdater createUpdateToolUpdater() throws Exception {
		return new AbstractJDBCUpdateToolUpdater() {
			@Override
			protected Connection getConnection() throws Exception {
				return AbstractJDBCSRMContext.this.getConnection();
			}

			@Override
			protected IJDBCEntityParserContext getParsers() throws Exception {
				return AbstractJDBCSRMContext.this.getParsers();
			}

			@Override
			protected IJDBCIdentifierParser getIdentifierParser()
					throws Exception {
				return AbstractJDBCSRMContext.this.getIdentifierParser();
			}

			@Override
			protected IModelUtils getModelUtils() throws Exception {
				return AbstractJDBCSRMContext.this.getUtils();
			}
		};
	}
}
