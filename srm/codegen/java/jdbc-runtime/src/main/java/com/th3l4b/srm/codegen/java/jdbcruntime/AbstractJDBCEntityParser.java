package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractJDBCEntityParser<R extends IRuntimeEntity<?>>
		implements IJDBCEntityParser<R> {

	protected IJDBCIdentifierParser _idsParser;
	protected IJDBCStatusParser _statusParser;

	public AbstractJDBCEntityParser(IJDBCIdentifierParser idsParser,
			IJDBCStatusParser statusParser) {
		_idsParser = idsParser;
		_statusParser = statusParser;
	}

	public IJDBCIdentifierParser getIdsParser() {
		return _idsParser;
	}

	public void setIdsParser(IJDBCIdentifierParser idsParser) {
		_idsParser = idsParser;
	}

	public IJDBCStatusParser getStatusParser() {
		return _statusParser;
	}

	public void setStatusParser(IJDBCStatusParser statusParser) {
		_statusParser = statusParser;
	}

	@Override
	public void parse(R entity, int index, ResultSet result) throws Exception {
		ICoordinates coordinates = entity.coordinates();
		coordinates.setIdentifier(getIdsParser().parse(index++, result));
		coordinates.setStatus(getStatusParser().parse(index++, result));
		parseRest(entity, index, result);
	}

	protected abstract void parseRest(R entity, int index, ResultSet result)
			throws Exception;

	@Override
	public void set(R entity, int index, PreparedStatement statement)
			throws Exception {
		ICoordinates coordinates = entity.coordinates();
		getIdsParser().set(coordinates.getIdentifier(), index++, statement);
		getStatusParser().set(coordinates.getStatus(), index++, statement);
		setRest(entity, index, statement);
	}

	protected abstract void setRest(R entity, int index,
			PreparedStatement statement) throws Exception;

}
