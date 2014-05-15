package com.th3l4b.srm.codegen.java.mongo.runtime;

import com.mongodb.DBObject;
import com.th3l4b.srm.codegen.java.basic.runtime.DefaultIdentifier;
import com.th3l4b.srm.runtime.AbstractEntityParser;
import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IDatabaseConstants;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractMongoEntityParser<R extends IRuntimeEntity<R>>
		extends
		AbstractEntityParser<IMongoIdentifierParser, IMongoStatusParser>
		implements IMongoEntityParser<R> {

	public AbstractMongoEntityParser(IMongoIdentifierParser idsParser,
			IMongoStatusParser statusParser) {
		super(idsParser, statusParser);
	}

	/**
	 * Always return true. This method does no check.
	 */
	@Override
	public boolean hasValue(Void arg, DBObject result) throws Exception {
		return true;
	}

	@Override
	public void set(R value, Void arg, DBObject obj) throws Exception {
		ICoordinates coordinates = value.coordinates();
		getIdsParser().set(coordinates.getIdentifier(), IDatabaseConstants.ID,
				obj);
		getStatusParser().set(coordinates.getStatus(),
				IDatabaseConstants.STATUS, obj);
		setRest(value, obj);
	}

	protected abstract void setRest(R value, DBObject statement)
			throws Exception;

	@Override
	public R parse(Void arg, DBObject result) throws Exception {
		R entity = create();
		ICoordinates coordinates = entity.coordinates();
		Class<R> clazz = entity.clazz();
		coordinates.setIdentifier(new DefaultIdentifier(clazz.getName(),
				getIdsParser().parse(IDatabaseConstants.ID, result).getKey()));
		coordinates.setStatus(getStatusParser().parse(
				IDatabaseConstants.STATUS, result));
		parseRest(entity, result);
		return entity;
	}

	protected abstract void parseRest(R entity, DBObject result)
			throws Exception;
}
