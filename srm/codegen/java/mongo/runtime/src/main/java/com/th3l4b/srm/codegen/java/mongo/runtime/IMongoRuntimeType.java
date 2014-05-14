package com.th3l4b.srm.codegen.java.mongo.runtime;

import com.mongodb.DBObject;
import com.th3l4b.srm.runtime.IBaseParser;

public interface IMongoRuntimeType<T> extends
		IBaseParser<T, DBObject, DBObject, String, String> {
}
