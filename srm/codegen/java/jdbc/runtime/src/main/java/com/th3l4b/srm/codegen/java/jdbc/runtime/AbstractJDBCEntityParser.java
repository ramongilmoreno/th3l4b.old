package com.th3l4b.srm.codegen.java.jdbc.runtime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.srm.codegen.java.basic.runtime.DefaultIdentifier;
import com.th3l4b.srm.runtime.AbstractEntityParser;
import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractJDBCEntityParser<R extends IRuntimeEntity<R>>
		extends AbstractEntityParser<IJDBCIdentifierParser, IJDBCStatusParser>
		implements IJDBCEntityParser<R> {

	public AbstractJDBCEntityParser(IJDBCIdentifierParser idsParser,
			IJDBCStatusParser statusParser) {
		super(idsParser, statusParser);
	}

	/**
	 * Always return true. This method does no check.
	 */
	@Override
	public boolean hasValue(Integer arg, ResultSet result) throws Exception {
		return true;
	}

	@Override
	public R parse(Integer idx, ResultSet result) throws Exception {
		int index = idx;
		R entity = create();
		ICoordinates coordinates = entity.coordinates();
		Class<R> clazz = entity.clazz();
		coordinates.setIdentifier(new DefaultIdentifier(clazz.getName(),
				getIdsParser().parse(index++, result).getKey()));
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

}
