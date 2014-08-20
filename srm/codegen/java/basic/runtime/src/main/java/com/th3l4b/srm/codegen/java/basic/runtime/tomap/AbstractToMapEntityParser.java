package com.th3l4b.srm.codegen.java.basic.runtime.tomap;

import java.util.Map;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.srm.codegen.java.basic.runtime.DefaultIdentifier;
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IDatabaseConstants;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.srm.runtime.IToMapEntityParser;

public abstract class AbstractToMapEntityParser<R extends IRuntimeEntity<R>>
		implements IToMapEntityParser<R> {

	private IToMapIdentifierParser _identifierParser;
	private IToMapStatusParser _statusParser;
	private String _name;

	public AbstractToMapEntityParser(String name,
			IToMapIdentifierParser identifierParser,
			IToMapStatusParser statusParser) throws Exception {
		_name = name;
		_identifierParser = identifierParser;
		_statusParser = statusParser;
	}

	public String getName() {
		return _name;
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
	public R parse(Void arg, Map<String, String> map) throws Exception {
		R r = create();
		// Check map type is the same as the class
		if (!NullSafe.equals(getName(), map.get(IDatabaseConstants.TYPE))) {
			throw new IllegalArgumentException(
					"Type does not match. Expected: " + getName() + ", found: "
							+ map.get(IDatabaseConstants.TYPE));
		}

		IToMapStatusParser statusParser = getStatusParser();
		String status = IDatabaseConstants.STATUS;
		if (statusParser.hasValue(status, map)) {
			r.coordinates().setStatus(statusParser.parse(status, map));
		} else {
			r.coordinates().setStatus(EntityStatus.Unknown);
		}
		String id = IDatabaseConstants.ID;
		IToMapIdentifierParser identifierParser = getIdentifierParser();
		if (identifierParser.hasValue(id, map)) {
			r.coordinates().setIdentifier(
					new DefaultIdentifier(r.clazz().getName(), identifierParser
							.parse(id, map).getKey()));
		}
		parseRest(r, map);
		return r;
	}

	@Override
	public boolean hasValue(Void arg, Map<String, String> result)
			throws Exception {
		return true;
	}

	@Override
	public void set(R value, Void arg, Map<String, String> map)
			throws Exception {
		getIdentifierParser().set(value.coordinates().getIdentifier(),
				IDatabaseConstants.ID, map);
		getStatusParser().set(value.coordinates().getStatus(),
				IDatabaseConstants.STATUS, map);
		map.put(IDatabaseConstants.TYPE, getName());
		setRest(value, map);
	}

}
