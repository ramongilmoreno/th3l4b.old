package com.th3l4b.srm.codegen.java.mongo.runtime.types;

import com.mongodb.DBObject;
import com.th3l4b.common.text.TextUtils;

public class MongoStringRuntimeType extends DefaultMongoRuntimeType<String> {

	private Integer _limit;

	public MongoStringRuntimeType(Integer limit) {
		// http://docs.mongodb.org/manual/reference/bson-types/
		super(2);
		_limit = limit;
	}

	@Override
	public String parse(String arg, DBObject result) throws Exception {
		String value = (String) result.get(arg);
		if ((value != null) && (_limit != null)) {
			value = TextUtils.limit(value, _limit.intValue());
		}
		return value;
	}

	@Override
	public void set(String value, String arg, DBObject object) throws Exception {
		if ((value != null) && (_limit != null)) {
			value = TextUtils.limit(value, _limit.intValue());
		}
		object.put(arg, value);
	}
}
