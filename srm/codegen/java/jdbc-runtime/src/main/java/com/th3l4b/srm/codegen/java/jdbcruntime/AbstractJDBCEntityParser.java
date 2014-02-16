package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractJDBCEntityParser<R extends IRuntimeEntity<R>>
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
	public R parse(Integer idx, ResultSet result) throws Exception {
		int index = idx;
		R entity = create();
		ICoordinates coordinates = entity.coordinates();
		Class<R> clazz = entity.clazz();
		coordinates.setIdentifier(getIdsParser().parse(index++, result));
		coordinates.getIdentifier().setType(clazz.getName());
		coordinates.setStatus(getStatusParser().parse(index++, result));
		parseRest(entity, index, result);
		return entity;
	}

	protected abstract void parseRest(R entity, int index, ResultSet result)
			throws Exception;

	@Override
	public void set(R entity, Integer idx, PreparedStatement statement)
			throws Exception {
		int index = idx;
		ICoordinates coordinates = entity.coordinates();
		getIdsParser().set(coordinates.getIdentifier(), index++, statement);
		getStatusParser().set(coordinates.getStatus(), index++, statement);
		setRest(entity, index, statement);
	}

	protected abstract void setRest(R entity, int index,
			PreparedStatement statement) throws Exception;
	
	String[] _allColumns;

	@Override
	public String[] allColumns() throws Exception {
		if (_allColumns == null) {
			String[] fieldsColumns = fieldsColumns();
			String[] r = new String[fieldsColumns.length + 2];
			r[0] = idColumn();
			r[1] = statusColumn();
			System.arraycopy(fieldsColumns, 0, r, 2, fieldsColumns.length);
			_allColumns = r;
		}
		return _allColumns;
	}
}
