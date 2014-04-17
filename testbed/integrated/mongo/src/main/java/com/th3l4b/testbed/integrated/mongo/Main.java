package com.th3l4b.testbed.integrated.mongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.th3l4b.testbed.integrated.model.AbstractIntegratedTestTests;
import com.th3l4b.testbed.integrated.model.generated.base.INameForIntegratedTestContext;
import com.th3l4b.testbed.integrated.model.generated.mongo.AbstractNameForIntegratedTestMongoContext;

/**
 * Tests the Mongo persistence facilities
 */
public class Main {
	public static void main(String[] args) throws Exception {
		int i = 0;
		String host = args[i++];
		int port = Integer.parseInt(args[i++]);
		String dbName = args[i++];
		MongoClient mongo = new MongoClient(host, port);
		final DB db = mongo.getDB(dbName);
		final INameForIntegratedTestContext context = new AbstractNameForIntegratedTestMongoContext() {
			@Override
			protected DB getDB() throws Exception {
				return db;
			}
		};

		AbstractIntegratedTestTests tests = new AbstractIntegratedTestTests() {
			@Override
			protected INameForIntegratedTestContext getContext()
					throws Exception {
				return context;
			}
		};
		tests.everything();
	}
}
