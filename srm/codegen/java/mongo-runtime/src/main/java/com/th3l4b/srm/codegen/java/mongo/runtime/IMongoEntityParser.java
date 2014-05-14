package com.th3l4b.srm.codegen.java.mongoruntime;

import com.mongodb.DBObject;
import com.th3l4b.srm.runtime.IDatabaseParser;

public interface IMongoEntityParser<R> extends
		IDatabaseParser<R, DBObject, DBObject, Void, Void> {
}
