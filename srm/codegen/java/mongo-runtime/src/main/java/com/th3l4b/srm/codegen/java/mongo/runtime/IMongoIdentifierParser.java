package com.th3l4b.srm.codegen.java.mongo.runtime;

import com.mongodb.DBObject;
import com.th3l4b.srm.runtime.IIdentifierParser;

public interface IMongoIdentifierParser extends
		IIdentifierParser<DBObject, DBObject, String, String> {
}
