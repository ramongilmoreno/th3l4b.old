package com.th3l4b.srm.codegen.java.mongoruntime;

import com.mongodb.DBObject;
import com.th3l4b.srm.runtime.IEntityStatusParser;

public interface IMongoStatusParser extends
		IEntityStatusParser<DBObject, DBObject, String, String> {
}
