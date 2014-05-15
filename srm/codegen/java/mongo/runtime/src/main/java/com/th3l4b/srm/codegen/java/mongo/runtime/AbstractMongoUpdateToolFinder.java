package com.th3l4b.srm.codegen.java.mongo.runtime;

import java.util.HashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.th3l4b.srm.codegen.java.basic.runtime.update.AbstractUpdateToolFinder;
import com.th3l4b.srm.runtime.IDatabaseConstants;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractMongoUpdateToolFinder extends
		AbstractUpdateToolFinder {

	protected abstract DB getDB() throws Exception;

	protected abstract IMongoEntityParserContext getParsers() throws Exception;

	protected abstract IMongoIdentifierParser getIdentifierParser()
			throws Exception;

	@Override
	protected <T extends IRuntimeEntity<T>> void findEntity(
			IRuntimeEntity<T> entity,
			HashMap<IIdentifier, IRuntimeEntity<?>> r, IModelUtils utils)
			throws Exception {

		BasicDBObject query = new BasicDBObject(1);
		IIdentifier id = entity.coordinates().getIdentifier();
		getIdentifierParser().set(id, IDatabaseConstants.ID, query);
		IMongoEntityParser<T> parser = getParsers().getEntityParser(
				entity.clazz());
		DBCursor cursor = getDB().getCollection(parser.table()).find(query);
		try {
			while (cursor.hasNext()) {
				DBObject next = cursor.next();
				T result = parser.parse(null, next);
				r.put(id, result);
				return;
			}
		} finally {
			cursor.close();
		}
	}
}
