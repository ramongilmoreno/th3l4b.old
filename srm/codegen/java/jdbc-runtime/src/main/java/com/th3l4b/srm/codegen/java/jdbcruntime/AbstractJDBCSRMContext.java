package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.Connection;

import com.th3l4b.srm.codegen.java.basicruntime.AbstractSRMContext;
import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolFinder;
import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolUpdater;
import com.th3l4b.types.runtime.IJDBCRuntimeTypesContext;

public abstract class AbstractJDBCSRMContext<FINDER> extends
		AbstractSRMContext<FINDER> {

	private IUpdateToolFinder _updateToolFinder;
	private IUpdateToolUpdater _updateToolUpdater;
	private IJDBCIdentifierParser _identifierParser;
	private IJDBCStatusParser _statusParser;
	private IJDBCEntityParserContext _parsers;
	private IJDBCRuntimeTypesContext _types;

	protected IJDBCIdentifierParser getIdentifierParser() throws Exception {
		if (_identifierParser == null) {
			_identifierParser = createIdentifierParser();
		}
		return _identifierParser;
	}

	protected IJDBCStatusParser getStatusParser() throws Exception {
		if (_statusParser == null) {
			_statusParser = createStatusParser();
		}
		return _statusParser;
	}

	protected IJDBCRuntimeTypesContext getTypes() throws Exception {
		if (_types == null) {
			_types = createTypes();
		}
		return _types;
	}

	protected IJDBCEntityParserContext getParsers() throws Exception {
		if (_parsers == null) {
			_parsers = createParsers();
		}
		return _parsers;
	}

	protected abstract Connection getConnection() throws Exception;

	protected abstract IJDBCIdentifierParser createIdentifierParser()
			throws Exception;

	protected abstract IJDBCStatusParser createStatusParser() throws Exception;

	protected abstract IJDBCRuntimeTypesContext createTypes() throws Exception;

	protected abstract IJDBCEntityParserContext createParsers()
			throws Exception;

	@Override
	protected IUpdateToolFinder getUpdateToolFinder() throws Exception {
		if (_updateToolFinder == null) {
			_updateToolFinder = new AbstractJDBCUpdateToolFinder() {
				@Override
				protected Connection getConnection() throws Exception {
					return AbstractJDBCSRMContext.this.getConnection();
				}

				@Override
				protected IJDBCEntityParserContext getParsers()
						throws Exception {
					return AbstractJDBCSRMContext.this.getParsers();
				}

				@Override
				protected IJDBCIdentifierParser getIdentifierParser()
						throws Exception {
					return AbstractJDBCSRMContext.this.getIdentifierParser();
				}
			};
		}
		return _updateToolFinder;
	}

	@Override
	protected IUpdateToolUpdater getUpdateToolUpdater() throws Exception {
		if (_updateToolUpdater == null) {
			_updateToolUpdater = new AbstractJDBCUpdateToolUpdater() {
				@Override
				protected Connection getConnection() throws Exception {
					return AbstractJDBCSRMContext.this.getConnection();
				}
			};
		}
		return _updateToolUpdater;
	}
}
