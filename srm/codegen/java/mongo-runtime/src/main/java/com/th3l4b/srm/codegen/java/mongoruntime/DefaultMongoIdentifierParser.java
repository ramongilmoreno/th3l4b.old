package com.th3l4b.srm.codegen.java.mongoruntime;

import com.mongodb.DBObject;
import com.th3l4b.srm.codegen.java.basicruntime.DefaultIdentifier;
import com.th3l4b.srm.runtime.IIdentifier;


public class DefaultMongoIdentifierParser implements IMongoIdentifierParser {

	@Override
	public IIdentifier parse(String arg, DBObject result) throws Exception {
		Object r = result.containsField(arg) ? result.get(arg) : null;
		if (r instanceof String) {
			return new DefaultIdentifier((String) r);
		} else {
			return null;
		}
	}

	@Override
	public boolean hasValue(String arg, DBObject result) throws Exception {
		return result.containsField(arg);
	}

	@Override
	public void set(IIdentifier value, String arg, DBObject statement)
			throws Exception {
		statement.put(arg, MongoUtils.fromIdentifier(value));		
	}
}
