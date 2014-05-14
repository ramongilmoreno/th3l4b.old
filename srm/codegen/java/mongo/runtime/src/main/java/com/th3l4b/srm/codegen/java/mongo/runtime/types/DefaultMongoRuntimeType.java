package com.th3l4b.srm.codegen.java.mongo.runtime.types;

import com.mongodb.DBObject;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoRuntimeType;

public class DefaultMongoRuntimeType<T> implements IMongoRuntimeType<T> {

	private int _bsonType;

	public DefaultMongoRuntimeType(int bsonType) {
		_bsonType = bsonType;
	}

	public int getBsonType() {
		return _bsonType;
	}

	@Override
	public boolean hasValue(String arg, DBObject result) throws Exception {
		return result.containsField(arg);
	}

	/**
	 * Take advantage of default handling of Java types by MongoDB.
	 */
	@Override
	public void set(T value, String arg, DBObject statement) throws Exception {
		statement.put(arg, value);
	}

	/**
	 * Take advantage of default handling of Java types by MongoDB.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T parse(String arg, DBObject result) throws Exception {
		return (T) result.get(arg);
	}

}
