package com.th3l4b.srm.codegen.java.mongo;

import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;

public class MongoCodeGeneratorContext extends JavaCodeGeneratorContext {

	private MongoNames _MongoNames;

	public MongoCodeGeneratorContext(BaseNames baseNames) {
		super(baseNames);
		_MongoNames = new MongoNames(baseNames);
	}

	public MongoNames getMongoNames() {
		return _MongoNames;
	}

	public void setMongoNames(MongoNames jDBCNames) {
		_MongoNames = jDBCNames;
	}

	public void copyTo(MongoCodeGeneratorContext to) throws Exception {
		super.copyTo(to);
		to.setMongoNames(getMongoNames());
	}
}
