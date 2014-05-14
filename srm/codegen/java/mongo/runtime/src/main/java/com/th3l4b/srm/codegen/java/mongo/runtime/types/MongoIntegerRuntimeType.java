package com.th3l4b.srm.codegen.java.mongo.runtime.types;

public class MongoIntegerRuntimeType extends DefaultMongoRuntimeType<Long> {

	public MongoIntegerRuntimeType() {
		// http://docs.mongodb.org/manual/reference/bson-types/
		super(18);
	}
}
