package com.th3l4b.srm.codegen.java.mongo.runtime;

import com.mongodb.DBObject;
import com.th3l4b.srm.runtime.IDatabaseParser;

public interface IMongoEntityParser<R> extends
		IDatabaseParser<R, DBObject, DBObject, Void, Void> {
	void unSetRest(R entity, DBObject result) throws Exception;
}
