package com.th3l4b.srm.codegen.java.basicruntime.update;

import com.th3l4b.srm.codegen.java.basicruntime.AbstractSRMContext;

public abstract class AbstractUpdateToolSRMContext<FINDER, IDENTIFIERPARSER, STATUSPARSER, ENTITYPARSERCONTEXT, RUNTIMETYPESCONTEXT>
		extends AbstractSRMContext<FINDER> {

	private IUpdateToolFinder _updateToolFinder;
	private IUpdateToolUpdater _updateToolUpdater;
	private IDENTIFIERPARSER _identifierParser;
	private STATUSPARSER _statusParser;
	private ENTITYPARSERCONTEXT _parsers;
	private RUNTIMETYPESCONTEXT _types;

	protected IDENTIFIERPARSER getIdentifierParser() throws Exception {
		if (_identifierParser == null) {
			_identifierParser = createIdentifierParser();
		}
		return _identifierParser;
	}

	protected STATUSPARSER getStatusParser() throws Exception {
		if (_statusParser == null) {
			_statusParser = createStatusParser();
		}
		return _statusParser;
	}

	protected RUNTIMETYPESCONTEXT getTypes() throws Exception {
		if (_types == null) {
			_types = createTypes();
		}
		return _types;
	}

	protected ENTITYPARSERCONTEXT getParsers() throws Exception {
		if (_parsers == null) {
			_parsers = createParsers();
		}
		return _parsers;
	}

	protected abstract IDENTIFIERPARSER createIdentifierParser()
			throws Exception;

	protected abstract STATUSPARSER createStatusParser() throws Exception;

	protected abstract RUNTIMETYPESCONTEXT createTypes() throws Exception;

	protected abstract ENTITYPARSERCONTEXT createParsers() throws Exception;

	protected abstract IUpdateToolFinder createUpdateToolFinder()
			throws Exception;

	@Override
	protected IUpdateToolFinder getUpdateToolFinder() throws Exception {
		if (_updateToolFinder == null) {
			_updateToolFinder = createUpdateToolFinder();
		}
		return _updateToolFinder;
	}

	protected abstract IUpdateToolUpdater createUpdateToolUpdater()
			throws Exception;

	@Override
	protected IUpdateToolUpdater getUpdateToolUpdater() throws Exception {
		if (_updateToolUpdater == null) {
			_updateToolUpdater = createUpdateToolUpdater();
		}
		return _updateToolUpdater;
	}

}
