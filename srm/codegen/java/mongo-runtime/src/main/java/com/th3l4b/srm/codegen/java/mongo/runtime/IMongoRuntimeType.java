package com.th3l4b.srm.codegen.java.mongoruntime;

import com.mongodb.DBObject;
import com.th3l4b.srm.runtime.IBaseParser;

public interface IMongoRuntimeType<T> extends
		IBaseParser<T, DBObject, DBObject, String, String> {
}
