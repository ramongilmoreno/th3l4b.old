package com.th3l4b.srm.codegen.java.basicruntime.tomap;

import java.util.Map;

import com.th3l4b.srm.runtime.IDatabaseConstants;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.srm.runtime.IToMapEntityParser;

public abstract class AbstractToMapEntityParser<R extends IRuntimeEntity<R>>
		implements IToMapEntityParser<R> {

	private IToMapIdentifierParser _identifierParser;
	private IToMapStatusParser _statusParser;

	public AbstractToMapEntityParser(IToMapIdentifierParser identifierParser,
			IToMapStatusParser statusParser) throws Exception {
		_identifierParser = identifierParser;
		_statusParser = statusParser;
	}

	public IToMapIdentifierParser getIdentifierParser() {
		return _identifierParser;
	}

	public IToMapStatusParser getStatusParser() {
		return _statusParser;
	}

	protected abstract R create() throws Exception;

	protected abstract void parseRest(R entity, Map<String, String> result)
			throws Exception;

	protected abstract void setRest(R value, Map<String, String> statement)
			throws Exception;

	@Override
	public R parse(Void arg, Map<String, String> result) throws Exception {
		R r = create();
		IToMapStatusParser statusParser = getStatusParser();
		String status = IDatabaseConstants.STATUS;
		if (statusParser.hasValue(status, result)) {
			r.coordinates().setStatus(statusParser.parse(status, result));
		}
		String id = IDatabaseConstants.ID;
		IToMapIdentifierParser identifierParser = getIdentifierParser();
		if (identifierParser.hasValue(id, result)) {
			r.coordinates().setIdentifier(identifierParser.parse(id, result));
			r.coordinates().getIdentifier().setType(r.clazz().getName());
		}
		parseRest(r, result);
		return r;
	}

	@Override
	public boolean hasValue(Void arg, Map<String, String> result)
			throws Exception {
		return true;
	}

	@Override
	public void set(R value, Void arg, Map<String, String> statement)
			throws Exception {
		getIdentifierParser().set(value.coordinates().getIdentifier(),
				IDatabaseConstants.ID, statement);
		getStatusParser().set(value.coordinates().getStatus(),
				IDatabaseConstants.STATUS, statement);
		setRest(value, statement);
	}

}
