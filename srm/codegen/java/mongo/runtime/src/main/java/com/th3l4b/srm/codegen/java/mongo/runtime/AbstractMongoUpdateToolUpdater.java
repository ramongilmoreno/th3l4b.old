package com.th3l4b.srm.codegen.java.mongo.runtime;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.th3l4b.srm.codegen.java.basic.runtime.update.AbstractUpdateToolUpdater;
import com.th3l4b.srm.runtime.IDatabaseConstants;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractMongoUpdateToolUpdater extends
		AbstractUpdateToolUpdater {

	protected abstract DB getDB() throws Exception;

	protected abstract IMongoEntityParserContext getParsers() throws Exception;

	protected abstract IMongoIdentifierParser getIdentifierParser()
			throws Exception;

	@Override
	protected <T extends IRuntimeEntity<T>> void updateEntity(T entity,
			T original, IModelUtils utils) throws Exception {
		IMongoEntityParser<T> parser = getParsers().getEntityParser(
				entity.clazz());
		DBObject set = new BasicDBObject();
		parser.set(entity, null, set);
		if (original == null) {
			// Insert
			getDB().getCollection(parser.table()).insert(set);
		} else {
			// Update
			// http://www.mkyong.com/mongodb/java-mongodb-update-document/
			DBObject q = new BasicDBObject();
			getIdentifierParser().set(entity.coordinates().getIdentifier(),
					IDatabaseConstants.ID, q);
			BasicDBObject u2 = new BasicDBObject();
			u2.append("$set", set);

			DBObject unSet = new BasicDBObject();
			parser.unSetRest(entity, unSet);
			u2.append("$unset", unSet);

			getDB().getCollection(parser.table()).update(q, u2);
		}
	}
}
