package com.th3l4b.srm.codegen.java.mongoruntime;

import com.mongodb.DBObject;
import com.th3l4b.srm.runtime.EntityStatus;

public class DefaultMongoStatusParser implements IMongoStatusParser {

	@Override
	public EntityStatus parse(String arg, DBObject result) throws Exception {
		Object status = result.containsField(arg) ? result.get(arg) : null;
		if (status instanceof String) {
			return EntityStatus.fromInitial((String) status);
		} else {
			return EntityStatus.Ignore;
		}
	}

	@Override
	public boolean hasValue(String arg, DBObject result) throws Exception {
		return result.containsField(arg);
	}

	@Override
	public void set(EntityStatus value, String arg, DBObject statement)
			throws Exception {
		statement.put(arg, MongoUtils.fromEntityStatus(value));
	}

}
