package com.th3l4b.srm.codegen.java.jdbcruntime.junit;

import java.sql.Connection;

import com.th3l4b.srm.codegen.java.jdbcruntime.AbstractJDBCSRMContext;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCEntityParserContext;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCIdentifierParser;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCRuntimeTypesContext;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCStatusParser;


public abstract class AbstractSampleJDBCSRMContext extends
		AbstractJDBCSRMContext<ISampleFinder> {

	@Override
	protected IJDBCEntityParserContext createParsers() throws Exception {
		return new SampleJDBCParsers(getIdentifierParser(), getStatusParser(),
				getTypes());
	}

	@Override
	protected ISampleFinder createFinder() throws Exception {
		return new AbstractSampleJDBCFinder() {
			@Override
			protected Connection getConnection() throws Exception {
				return AbstractSampleJDBCSRMContext.this.getConnection();
			}

			@Override
			protected IJDBCIdentifierParser getIdentifierParser()
					throws Exception {
				return AbstractSampleJDBCSRMContext.this.getIdentifierParser();
			}

			@Override
			protected IJDBCStatusParser getStatusParser() throws Exception {
				return AbstractSampleJDBCSRMContext.this.getStatusParser();
			}

			@Override
			protected IJDBCRuntimeTypesContext getTypes() throws Exception {
				return AbstractSampleJDBCSRMContext.this.getTypes();
			}

			@Override
			protected IJDBCEntityParserContext getParsers() throws Exception {
				return AbstractSampleJDBCSRMContext.this.getParsers();
			}
		};
	}

}
